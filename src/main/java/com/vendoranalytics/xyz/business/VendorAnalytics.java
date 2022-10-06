package com.vendoranalytics.xyz.business;

import com.vendoranalytics.xyz.modal.Employee;
import com.vendoranalytics.xyz.modal.EmployeeRow;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class VendorAnalytics {

    HashMap<String, Employee> employeeHashMap = new HashMap<>();
    ArrayList<EmployeeRow> employeeArrayList = new ArrayList<>();
    LinkedHashMap<String, ArrayList<String>> empToManagerMap = new LinkedHashMap<>();
    HashMap<String, ArrayList<String>> offShoreMap = new HashMap<>();
    HashMap<String, ArrayList<String>> onsiteMap = new HashMap<>();
    ArrayList<String> offShoreLocations = new ArrayList<>();
    HashMap<String, HeadCount> returnMap = new LinkedHashMap<>();

    EmployeeRow employeeRow;
    private List<String> users;
    private String inDirectoryPath;
    private String outDirectoryPath;

    public VendorAnalytics(String inDirectoryPath, String outDirectoryPath, List<String> users) {
        this.inDirectoryPath = inDirectoryPath;
        this.outDirectoryPath = outDirectoryPath;
        this.users = users;
    }

    public void startAnalysis() {
        this.setOffShoreLocations();
        this.analyzeExcelAndMapEmployeeInformation();
        this.formHierarchyLevels();
        this.exportVendorDataToExcel();
    }

    private String timeStamp() {
        LocalDate date = LocalDate.now();
        String FORMAT = "dd_MM_yyyy_HHmmss";
        Instant instant = Instant.now();
        long timeStampMillis = instant.toEpochMilli();
        ZoneId zone = ZoneId.systemDefault();
        DateTimeFormatter df = DateTimeFormatter.ofPattern(FORMAT).withZone(zone);
        String time = df.format(Instant.ofEpochMilli(timeStampMillis));
        return time;
    }

    private void exportVendorDataToExcel() {
        try {
            if (!Files.isDirectory(Paths.get(outDirectoryPath))) {
                throw new IllegalArgumentException("Path must be a directory!");
            }

            String filename = outDirectoryPath + "\\vendor_analysis_" + timeStamp() + ".xlsx";
            System.out.println(filename);
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Vendor Analysis");
            XSSFRow rowhead = sheet.createRow((short) 0);
            rowhead.createCell(0).setCellValue("Cisco BU");
            rowhead.createCell(1).setCellValue("L1");
            rowhead.createCell(2).setCellValue("L2");
            rowhead.createCell(3).setCellValue("L3");
            rowhead.createCell(4).setCellValue("L4");
            rowhead.createCell(5).setCellValue("Vendor Name");
            rowhead.createCell(6).setCellValue("HC On");
            rowhead.createCell(7).setCellValue("HC Off");
            rowhead.createCell(8).setCellValue("Key skills");
            rowhead.createCell(9).setCellValue("Key service Offering");
            rowhead.createCell(10).setCellValue("why Cisco is working with this supplier ?");
            rowhead.createCell(11).setCellValue("Current TCO");
            rowhead.createCell(12).setCellValue("LTI Target Y/N");
            rowhead.createCell(13).setCellValue("LTI proposed TCO");
            rowhead.createCell(14).setCellValue("Why LTI");

            for (int i = 0; i < employeeArrayList.size(); i++) {
                XSSFRow row = sheet.createRow((short) i + 1);
                row.createCell(0).setCellValue("");
                row.createCell(1).setCellValue(employeeArrayList.get(i).getLevel1());
                row.createCell(2).setCellValue(employeeArrayList.get(i).getLevel2());
                row.createCell(3).setCellValue(employeeArrayList.get(i).getLevel3());
                row.createCell(4).setCellValue(employeeArrayList.get(i).getLevel4());
                row.createCell(5).setCellValue(employeeArrayList.get(i).getVendor());
                if (employeeArrayList.get(i).getHeadCountOffshore() != null) {
                    row.createCell(6).setCellValue(employeeArrayList.get(i).getHeadCountOffshore());
                }
                if (employeeArrayList.get(i).getHeadCountOnsite() != null) {
                    row.createCell(7).setCellValue(employeeArrayList.get(i).getHeadCountOnsite());
                }

            }

            for (int i = 0; i < 14; i++) {
                sheet.autoSizeColumn(i);
            }
            FileOutputStream fileOut = new FileOutputStream(filename);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
            System.out.println("Excel file has been generated successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void formHierarchyLevels() {
        for (int i = 0; i < users.size(); i++) {
            String firstLevelManager = users.get(i);
            ArrayList<String> firstLevelManagers = filterEmployeesByType(empToManagerMap.get(firstLevelManager), "regular");
            if (firstLevelManagers != null && firstLevelManagers.size() > 0) {
                ArrayList<String> directVendorEmployees = filterEmployeesByType(empToManagerMap.get(firstLevelManager), "vendor");
                if (directVendorEmployees.size() > 0) {
                    appendEmployeeRowsDirectlyUnderLevel1(firstLevelManager, directVendorEmployees);
                }
                for (int j = 0; j < firstLevelManagers.size(); j++) {
                    String secondLevelManager = firstLevelManagers.get(j);
                    ArrayList<String> secondLevelManagers = filterEmployeesByType(empToManagerMap.get(secondLevelManager), "regular");
                    if (secondLevelManagers != null && secondLevelManagers.size() > 0) {
                        ArrayList<String> directVendorEmployees2 = filterEmployeesByType(empToManagerMap.get(secondLevelManager), "vendor");
                        if (directVendorEmployees2.size() > 0) {
                            appendEmployeeRowsDirectlyUnderLevel2(firstLevelManager, secondLevelManager, directVendorEmployees2);
                        }
                        for (int k = 0; k < secondLevelManagers.size(); k++) {
                            String thirdLevelManager = secondLevelManagers.get(k);
                            ArrayList<String> thirdLevelManagers = filterEmployeesByType(empToManagerMap.get(thirdLevelManager), "regular");
                            if (thirdLevelManagers != null && thirdLevelManagers.size() > 0) {
                                for (int l = 0; l < thirdLevelManagers.size(); l++) {
                                    ArrayList<String> directVendorEmployees3 = filterEmployeesByType(empToManagerMap.get(thirdLevelManager), "vendor");
                                    if (directVendorEmployees3.size() > 0) {
                                        appendEmployeeRowsDirectlyUnderLevel3(firstLevelManager, secondLevelManager, thirdLevelManager, directVendorEmployees3);
                                    }
                                    String fourthLevelManger = thirdLevelManagers.get(l);
                                    appendFourthLevelRows(firstLevelManager, secondLevelManager, thirdLevelManager, fourthLevelManger);
                                }
                            } else {
                                appendThirdLevelRows(firstLevelManager, secondLevelManager, thirdLevelManager);
                            }
                        }
                    } else {
                        appendSecondLevelRows(firstLevelManager, secondLevelManager);
                    }
                }
            } else {
                appendFirstLevelEmployeeRows(firstLevelManager);
            }
        }
    }

    private void appendFourthLevelRows(String firstLevelManager, String secondLevelManager, String thirdLevelManager, String fourthLevelManger) {
        recursivelyGetTheVendorsInformation(fourthLevelManger);
        Map<String, HeadCount> returnMap = fetchVendorCount();
        EmployeeRow employee4Row;
        if (returnMap.size() > 0) {
            for (Map.Entry<String, HeadCount> entry : returnMap.entrySet()) {
                employee4Row = new EmployeeRow();
                employee4Row.setLevel1(employeeHashMap.get(firstLevelManager).getFullNameWithTitle());
                employee4Row.setLevel2(employeeHashMap.get(secondLevelManager).getFullNameWithTitle());
                employee4Row.setLevel3(employeeHashMap.get(thirdLevelManager).getFullNameWithTitle());
                employee4Row.setLevel4(employeeHashMap.get(fourthLevelManger).getFullNameWithTitle());
                employee4Row.setVendor(entry.getKey());
                employee4Row.setHeadCountOffshore(entry.getValue().getOffShore());
                employee4Row.setHeadCountOnsite(entry.getValue().getOnSite());
                employeeArrayList.add(employee4Row);
            }
        } else {
            employee4Row = new EmployeeRow();
            employee4Row.setLevel1(employeeHashMap.get(firstLevelManager).getFullNameWithTitle());
            employee4Row.setLevel2(employeeHashMap.get(secondLevelManager).getFullNameWithTitle());
            employee4Row.setLevel3(employeeHashMap.get(thirdLevelManager).getFullNameWithTitle());
            employee4Row.setLevel4(employeeHashMap.get(fourthLevelManger).getFullNameWithTitle());
            employeeArrayList.add(employee4Row);
        }
        clearVendorMap();
    }

    private void appendThirdLevelRows(String firstLevelManager, String secondLevelManager, String thirdLevelManager) {
        recursivelyGetTheVendorsInformation(thirdLevelManager);
        Map<String, HeadCount> returnMap = fetchVendorCount();
        EmployeeRow employee3Row;
        if (returnMap.size() > 0) {
            for (Map.Entry<String, HeadCount> entry : returnMap.entrySet()) {
                employee3Row = new EmployeeRow();
                employee3Row.setLevel1(employeeHashMap.get(firstLevelManager).getFullNameWithTitle());
                employee3Row.setLevel2(employeeHashMap.get(secondLevelManager).getFullNameWithTitle());
                employee3Row.setLevel3(employeeHashMap.get(thirdLevelManager).getFullNameWithTitle());
                employee3Row.setVendor(entry.getKey());
                employee3Row.setHeadCountOffshore(entry.getValue().getOffShore());
                employee3Row.setHeadCountOnsite(entry.getValue().getOnSite());
                employeeArrayList.add(employee3Row);
            }
        } else {
            employee3Row = new EmployeeRow();
            employee3Row.setLevel1(employeeHashMap.get(firstLevelManager).getFullNameWithTitle());
            employee3Row.setLevel2(employeeHashMap.get(secondLevelManager).getFullNameWithTitle());
            employee3Row.setLevel3(employeeHashMap.get(thirdLevelManager).getFullNameWithTitle());
            employeeArrayList.add(employee3Row);
        }
        clearVendorMap();
    }

    private void appendSecondLevelRows(String firstLevelManager, String secondLevelManager) {
        recursivelyGetTheVendorsInformation(secondLevelManager);
        Map<String, HeadCount> returnMap = fetchVendorCount();
        EmployeeRow employee2Row;
        if (returnMap.size() > 0) {
            for (Map.Entry<String, HeadCount> entry : returnMap.entrySet()) {
                employee2Row = new EmployeeRow();
                employee2Row.setLevel1(employeeHashMap.get(firstLevelManager).getFullNameWithTitle());
                employee2Row.setLevel2(employeeHashMap.get(secondLevelManager).getFullNameWithTitle());
                employee2Row.setVendor(entry.getKey());
                employee2Row.setHeadCountOffshore(entry.getValue().getOffShore());
                employee2Row.setHeadCountOnsite(entry.getValue().getOnSite());
                employeeArrayList.add(employee2Row);
            }
        } else {
            employee2Row = new EmployeeRow();
            employee2Row.setLevel1(employeeHashMap.get(firstLevelManager).getFullNameWithTitle());
            employee2Row.setLevel2(employeeHashMap.get(secondLevelManager).getFullNameWithTitle());
            employeeArrayList.add(employee2Row);
        }
        clearVendorMap();
    }

    private void appendFirstLevelEmployeeRows(String firstLevelManager) {
        recursivelyGetTheVendorsInformation(firstLevelManager);
        Map<String, HeadCount> returnMap = fetchVendorCount();
        EmployeeRow employeeRow;
        if (returnMap.size() > 0) {
            for (Map.Entry<String, HeadCount> entry : returnMap.entrySet()) {
                employeeRow = new EmployeeRow();
                employeeRow.setLevel1(employeeHashMap.get(firstLevelManager).getFullNameWithTitle());
                employeeRow.setVendor(entry.getKey());
                employeeRow.setHeadCountOffshore(entry.getValue().getOffShore());
                employeeRow.setHeadCountOnsite(entry.getValue().getOnSite());
                employeeArrayList.add(employeeRow);
            }
        } else {
            employeeRow = new EmployeeRow();
            employeeRow.setLevel1(employeeHashMap.get(firstLevelManager).getFullNameWithTitle());
            employeeArrayList.add(employeeRow);
        }
        clearVendorMap();
    }

    private void appendEmployeeRowsDirectlyUnderLevel1(String firstLevelManager, ArrayList<String> directVendorEmployees) {
        fetchVendorsUnderTheManager(directVendorEmployees);
        Map<String, HeadCount> returnMap = fetchVendorCount();
        EmployeeRow employeeRow;
        if (returnMap.size() > 0) {
            for (Map.Entry<String, HeadCount> entry : returnMap.entrySet()) {
                employeeRow = new EmployeeRow();
                employeeRow.setLevel1(employeeHashMap.get(firstLevelManager).getFullNameWithTitle());
                employeeRow.setVendor(entry.getKey());
                employeeRow.setHeadCountOffshore(entry.getValue().getOffShore());
                employeeRow.setHeadCountOnsite(entry.getValue().getOnSite());
                employeeArrayList.add(employeeRow);
            }
        }
        clearVendorMap();
    }

    private void appendEmployeeRowsDirectlyUnderLevel2(String firstLevelManager, String secondLevelManager, ArrayList<String> directVendorEmployees) {
        fetchVendorsUnderTheManager(directVendorEmployees);
        Map<String, HeadCount> returnMap = fetchVendorCount();
        EmployeeRow employee2Row;
        if (returnMap.size() > 0) {
            for (Map.Entry<String, HeadCount> entry : returnMap.entrySet()) {
                employee2Row = new EmployeeRow();
                employee2Row.setLevel1(employeeHashMap.get(firstLevelManager).getFullNameWithTitle());
                employee2Row.setLevel2(employeeHashMap.get(secondLevelManager).getFullNameWithTitle());
                employee2Row.setVendor(entry.getKey());
                employee2Row.setHeadCountOffshore(entry.getValue().getOffShore());
                employee2Row.setHeadCountOnsite(entry.getValue().getOnSite());
                employeeArrayList.add(employee2Row);
            }
        }
        clearVendorMap();
    }

    private void appendEmployeeRowsDirectlyUnderLevel3(String firstLevelManager, String secondLevelManager, String thirdLevelManager, ArrayList<String> directVendorEmployees) {
        fetchVendorsUnderTheManager(directVendorEmployees);
        Map<String, HeadCount> returnMap = fetchVendorCount();
        EmployeeRow employee3Row;
        if (returnMap.size() > 0) {
            for (Map.Entry<String, HeadCount> entry : returnMap.entrySet()) {
                employee3Row = new EmployeeRow();
                employee3Row.setLevel1(employeeHashMap.get(firstLevelManager).getFullNameWithTitle());
                employee3Row.setLevel2(employeeHashMap.get(secondLevelManager).getFullNameWithTitle());
                employee3Row.setLevel3(employeeHashMap.get(thirdLevelManager).getFullNameWithTitle());
                employee3Row.setVendor(entry.getKey());
                employee3Row.setHeadCountOffshore(entry.getValue().getOffShore());
                employee3Row.setHeadCountOnsite(entry.getValue().getOnSite());
                employeeArrayList.add(employee3Row);
            }
        }
        clearVendorMap();
    }

    public ArrayList<String> filterEmployeesByType(ArrayList<String> employees, String filterType) {
        if (employees == null || employees.size() == 0) {
            return new ArrayList<>();
        }
        ArrayList<String> employeeListArray = new ArrayList<>();
        for (String employee : employees) {
            Employee employeeObj = employeeHashMap.get(employee);
//            System.out.println(employeeObj);
            if (employeeObj.getType().equalsIgnoreCase(filterType)) {
                employeeListArray.add(employee);
            }
        }
        return employeeListArray;
    }

    private void clearVendorMap() {
        offShoreMap = new HashMap<>();
        onsiteMap = new HashMap<>();
        returnMap = new LinkedHashMap<>();
    }

    private void fetchVendorsUnderTheManager(ArrayList<String> vendorEmployees) {
        if (vendorEmployees != null && vendorEmployees.size() > 0) {
            for (int j = 0; j < vendorEmployees.size(); j++) {
                Employee employee = employeeHashMap.get(vendorEmployees.get(j));
                if (employee != null) {
                    if (offShoreLocations.indexOf(employee.getSite()) != -1) {
                        if (offShoreMap.get(employee.getVendor()) == null) {
                            ArrayList<String> employees = new ArrayList<>();
                            employees.add(employee.getUserName());
                            offShoreMap.put(employee.getVendor(), employees);
                        } else {
                            offShoreMap.get(employee.getVendor()).add(employee.getUserName());
                        }
                    } else {
                        if (onsiteMap.get(employee.getVendor()) == null) {
                            ArrayList<String> employees = new ArrayList<>();
                            employees.add(employee.getUserName());
                            onsiteMap.put(employee.getVendor(), employees);
                        } else {
                            onsiteMap.get(employee.getVendor()).add(employee.getUserName());
                        }
                    }
                }
            }
        }
    }

    private void recursivelyGetTheVendorsInformation(String rootManager) {
        ArrayList<String> subordinates = empToManagerMap.get(rootManager);
        if (subordinates != null && subordinates.size() > 0) {
            for (int j = 0; j < subordinates.size(); j++) {
                Employee employee = employeeHashMap.get(subordinates.get(j));
                if (employee != null && employee.getType().equalsIgnoreCase("vendor")) {
                    if (offShoreLocations.indexOf(employee.getSite()) != -1) {
                        if (offShoreMap.get(employee.getVendor()) == null) {
                            ArrayList<String> employees = new ArrayList<>();
                            employees.add(employee.getUserName());
                            offShoreMap.put(employee.getVendor(), employees);
                        } else {
                            offShoreMap.get(employee.getVendor()).add(employee.getUserName());
                        }
                    } else {
                        if (onsiteMap.get(employee.getVendor()) == null) {
                            ArrayList<String> employees = new ArrayList<>();
                            employees.add(employee.getUserName());
                            onsiteMap.put(employee.getVendor(), employees);
                        } else {
                            onsiteMap.get(employee.getVendor()).add(employee.getUserName());
                        }
                    }
                }
                recursivelyGetTheVendorsInformation(subordinates.get(j));
            }
        }
    }

    private HashMap fetchVendorCount() {
        String[] offShoreKeyArray = offShoreMap.keySet().toArray(new String[offShoreMap.size()]);
        String[] onsiteKeyArray = onsiteMap.keySet().toArray(new String[onsiteMap.size()]);
        List list = new ArrayList(Arrays.asList(offShoreKeyArray));
        list.addAll(Arrays.asList(onsiteKeyArray));
        List<String> distinctElements = (List<String>) list.stream().distinct().collect(Collectors.toList());
        for (String distinctElement : distinctElements
        ) {
            Integer offshoreHeadCount = offShoreMap.get(distinctElement) != null ? offShoreMap.get(distinctElement).size() : 0;
            Integer onsiteHeadCount = onsiteMap.get(distinctElement) != null ? onsiteMap.get(distinctElement).size() : 0;
            returnMap.put(distinctElement, new HeadCount(offshoreHeadCount, onsiteHeadCount));
        }
        return returnMap;
    }

    private void setOffShoreLocations() {
        offShoreLocations.add("BANGALORE");
        offShoreLocations.add("MUMBAI");
        offShoreLocations.add("PUNE");
        offShoreLocations.add("LUCKNOW");
        offShoreLocations.add("NOIDA");
        offShoreLocations.add("GURGAON");
        offShoreLocations.add("NEW DELHI");
        offShoreLocations.add("CHENNAI");
    }

    private void analyzeExcelAndMapEmployeeInformation() {
        if (!Files.isDirectory(Paths.get(inDirectoryPath))) {
            throw new IllegalArgumentException("Path must be a directory!");
        }
        try (Stream<Path> paths = Files.walk(Paths.get(inDirectoryPath), 1)) {
            paths
                    .filter(Files::isRegularFile)
                    .filter(p -> p.getFileName().toString().endsWith(".xlsx"))
                    .forEach(path -> extractEmployeeInformation(String.valueOf(path)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void extractEmployeeInformation(String fileName) {
        System.out.println(fileName);
        try {
            File file = new File(fileName);   //creating a new file instance
            FileInputStream fis = new FileInputStream(file);   //obtaining bytes from the file
            //creating Workbook instance that refers to .xlsx file
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sheet = wb.getSheetAt(0);     //creating a Sheet object to retrieve object
            Iterator<Row> itr = sheet.iterator();    //iterating over excel file
            List<Employee> listOfAllEmployees = new ArrayList<>();
            while (itr.hasNext()) {
                Row row = itr.next();
                if (row.getRowNum() != 0) {
                    Employee employee = convertRowToObject(row);
                    if (isActiveUser(employee)) {
                        listOfAllEmployees.add(employee);
//                        employeeHashMap.put(employee.getUserName(), employee);
//                        String empManager = employee.getManager();
//                        if (empToManagerMap.containsKey(empManager)) {
//                            empToManagerMap.get(empManager).add(employee.getUserName());
//                        } else {
//                            ArrayList<String> empList = new ArrayList<>();
//                            empList.add(employee.getUserName());
//                            empToManagerMap.put(employee.getManager(), empList);
//                        }
                    }
                }
            }
            formManagerToEmployeeMappings(listOfAllEmployees);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void formManagerToEmployeeMappings(List<Employee> listOfAllEmployees) {
        for (Employee employee : listOfAllEmployees
        ) {
            employeeHashMap.put(employee.getUserName(), employee);
            String empManager = employee.getManager();
            if (empToManagerMap.containsKey(empManager)) {
                empToManagerMap.get(empManager).add(employee.getUserName());
            } else {
                ArrayList<String> empList = new ArrayList<>();
                empList.add(employee.getUserName());
                empToManagerMap.put(employee.getManager(), empList);
            }
        }
    }

    private boolean isActiveUser(Employee employee) {
        return employee.getStatus().equalsIgnoreCase("Active");
    }

    private Employee convertRowToObject(Row row) {
        Employee employee = new Employee();
        Iterator<Cell> cellIterator = row.cellIterator();   //iterating over each column
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            int index = cell.getColumnIndex();
            if (index == 0) {
                employee.setUserName(cell.getStringCellValue());
            }

            if (index == 1) {
                String employeeNumber = cell.getStringCellValue();
                if (!employeeNumber.trim().equalsIgnoreCase("-")) {
                    employee.setEmployeeNumber(Integer.valueOf(cell.getStringCellValue()));
                }
            }
            if (index == 2) {
                employee.setFirstName(cell.getStringCellValue());
            }
            if (index == 3) {
                employee.setLastName(cell.getStringCellValue());
            }
            if (index == 4) {
                employee.setStatus(cell.getStringCellValue());
            }
            if (index == 5) {
                employee.setType(cell.getStringCellValue());
            }
            if (index == 6) {
                employee.setManager(cell.getStringCellValue());
            }
            if (index == 7) {
                String deptNumber = cell.getStringCellValue();
                if (!deptNumber.trim().equalsIgnoreCase("-")) {
                    employee.setDepartmentNumber(Integer.valueOf(cell.getStringCellValue()));
                }
            }
            if (index == 8) {
                employee.setDepartment(cell.getStringCellValue());
            }
            if (index == 9) {
                employee.setTitle(cell.getStringCellValue());
            }
            if (index == 10) {
                employee.setDeskPhone(cell.getStringCellValue());
            }
            if (index == 11) {
                employee.setBuilding(cell.getStringCellValue());
            }
            if (index == 12) {
                employee.setSite(cell.getStringCellValue());
            }
            if (index == 13) {
                employee.setVendor(cell.getStringCellValue());
            }
        }
        return employee;
    }
}
