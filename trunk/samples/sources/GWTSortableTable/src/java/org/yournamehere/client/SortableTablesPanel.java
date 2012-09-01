package org.yournamehere.client;

import com.google.gwt.user.client.ui.Composite;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HTMLTable.RowFormatter;

public class SortableTablesPanel extends Composite {

    private int sortDirection = 0;
    private FlexTable sortableTable = new FlexTable();
    private String[] columnHeaders = new String[]{"First Name", "Last Name",
        "City", "Country"
    };
    private ArrayList customerData = new ArrayList();
    private HashMap dataBucket = new HashMap();
    private ArrayList sortColumnValues = new ArrayList();

    private class CustomerData {

        private String firstName;
        private String lastName;
        private String country;
        private String city;

        public CustomerData(String firstName, String lastName, String city,
                String country) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.country = country;
            this.city = city;
        }

        public String getCountry() {
            return country;
        }

        public String getCity() {
            return city;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }
    }

    public SortableTablesPanel() {

        VerticalPanel workPanel = new VerticalPanel();

        sortableTable.setWidth(500 + "px");
        sortableTable.setStyleName("sortableTable");
        sortableTable.setBorderWidth(1);
        sortableTable.setCellPadding(4);
        sortableTable.setCellSpacing(1);

        sortableTable.setHTML(0, 0, columnHeaders[0] + "&nbsp;<img border='0' src='images/blank.gif'/>");
        sortableTable.setHTML(0, 1, columnHeaders[1] + "&nbsp;<img border='0' src='images/blank.gif'/>");
        sortableTable.setHTML(0, 2, columnHeaders[2] + "&nbsp;<img border='0' src='images/blank.gif'/>");
        sortableTable.setHTML(0, 3, columnHeaders[3] + "&nbsp;<img border='0' src='images/blank.gif'/>");

        customerData.add(new CustomerData("Rahul", "Dravid", "Bangalore",
                "India"));
        customerData.add(new CustomerData("Nat", "Flintoff", "London",
                "England"));
        customerData.add(new CustomerData("Inzamamul", "Haq", "Lahore",
                "Pakistan"));
        customerData.add(new CustomerData("Graeme", "Smith", "Durban",
                "SouthAfrica"));
        customerData.add(new CustomerData("Ricky", "Ponting", "Sydney",
                "Australia"));

        int row = 1;
        for (Iterator iter = customerData.iterator(); iter.hasNext();) {
            CustomerData element = (CustomerData) iter.next();
            sortableTable.setText(row, 0, element.getFirstName());
            sortableTable.setText(row, 1, element.getLastName());
            sortableTable.setText(row, 2, element.getCity());
            sortableTable.setText(row, 3, element.getCountry());
            row++;
        }

        RowFormatter rowFormatter = sortableTable.getRowFormatter();
        rowFormatter.setStyleName(0, "tableHeader");

        sortableTable.addTableListener(new TableListener() {

            public void onCellClicked(SourcesTableEvents sender, int row,
                    int cell) {
                if (row == 0) {
                    sortTable(row, cell);
                }
            }
        });

        workPanel.setStyleName("sortableTables-Panel");
        workPanel.add(sortableTable);
        DockPanel workPane = new DockPanel();
        workPane.add(workPanel, DockPanel.CENTER);
        workPane.setCellHeight(workPanel, "100%");
        workPane.setCellWidth(workPanel, "100%");
        sortTable(0, 0);

        initWidget(workPane);
    }

    public void sortTable(int row, int cell) {
        dataBucket.clear();
        sortColumnValues.clear();

        for (int i = 1; i < customerData.size() + 1; i++) {
            dataBucket.put(sortableTable.getText(i, cell), new CustomerData(
                    sortableTable.getText(i, 0), sortableTable.getText(i, 1),
                    sortableTable.getText(i, 2), sortableTable.getText(i, 3)));
            sortColumnValues.add(sortableTable.getText(i, cell));
        }

        if (sortDirection == 0) {
            sortDirection = 1;
            Collections.sort(sortColumnValues);

        } else {
            sortDirection = 0;
            Collections.reverse(sortColumnValues);
        }
        redrawColumnHeaders(cell);
        redrawTable();
    }

    private void redrawTable() {
        int row = 1;
        for (Iterator iter = sortColumnValues.iterator(); iter.hasNext();) {
            String key = (String) iter.next();
            CustomerData custData = (CustomerData) dataBucket.get(key);

            sortableTable.setText(row, 0, custData.getFirstName());
            sortableTable.setText(row, 1, custData.getLastName());
            sortableTable.setText(row, 2, custData.getCity());
            sortableTable.setText(row, 3, custData.getCountry());
            row++;
        }
    }

    private void redrawColumnHeaders(int column) {
        if (sortDirection == 0) {
            sortableTable.setHTML(0, column, columnHeaders[column] + "&nbsp;<img border='0' src='images/desc.gif'/>");
        } else if (sortDirection == 1) {
            sortableTable.setHTML(0, column, columnHeaders[column] + "&nbsp;<img border='0' src='images/asc.gif'/>");
        } else {
            sortableTable.setHTML(0, column, columnHeaders[column] + "&nbsp;<img border='0' src='images/blank.gif'/>");
        }
        for (int i = 0; i < 4; i++) {
            if (i != column) {
                sortableTable.setHTML(0, i, columnHeaders[i] + "&nbsp;<img border='0' src='images/blank.gif'/>");
            }
        }
    }

    public void onShow() {
    }
}
