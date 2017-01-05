/*
 * GWTServiceImpl.java
 *
 * Created on December 11, 2007, 5:05 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package org.yournamehere.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.yournamehere.client.GWTService;

/**
 *
 * @author gw152771
 */
public class GWTServiceImpl extends RemoteServiceServlet implements GWTService {

    private HashMap data = new HashMap();

    private class Manufacturer {

        private HashMap brands = new HashMap();

        public Manufacturer(HashMap brands) {
            this.brands = brands;
        }

        public HashMap getBrands() {
            return brands;
        }
    }

    public GWTServiceImpl() {
        super();
        loadData();
    }

    private void loadData() {

        ArrayList brandModels = new ArrayList();
        brandModels.add("EX");
        brandModels.add("DX Hatchback");
        brandModels.add("DX 4-Door");
        HashMap manufacturerBrands = new HashMap();
        manufacturerBrands.put("Civic", brandModels);
        brandModels = new ArrayList();
        brandModels.add("SX");
        brandModels.add("Sedan");
        manufacturerBrands.put("Accord", brandModels);
        brandModels = new ArrayList();
        brandModels.add("LX");
        brandModels.add("Deluxe");
        manufacturerBrands.put("Odyssey", brandModels);
        Manufacturer manufacturer = new Manufacturer(manufacturerBrands);
        data.put("Honda", manufacturer);

        brandModels = new ArrayList();
        brandModels.add("LXE");
        brandModels.add("LX");
        manufacturerBrands = new HashMap();
        manufacturerBrands.put("Altima", brandModels);
        brandModels = new ArrayList();
        brandModels.add("NX");
        brandModels.add("EXE");
        manufacturerBrands.put("Sentra", brandModels);
        manufacturer = new Manufacturer(manufacturerBrands);
        data.put("Nissan", manufacturer);

        brandModels = new ArrayList();
        brandModels.add("E300");
        brandModels.add("E500");
        manufacturerBrands = new HashMap();
        manufacturerBrands.put("E-Class", brandModels);
        brandModels = new ArrayList();
        brandModels.add("C250");
        brandModels.add("C300");
        manufacturerBrands.put("C-Class", brandModels);
        manufacturer = new Manufacturer(manufacturerBrands);
        data.put("Mercedes", manufacturer);
    }

    public ArrayList getBrands(String manufacturer) {
        ArrayList brandsList = new ArrayList();
        for (Iterator iter = ((Manufacturer) data.get(manufacturer)).getBrands().keySet().iterator(); iter.hasNext();) {
            brandsList.add((String) iter.next());
        }
        return brandsList;
    }

    public ArrayList getManufacturers() {
        ArrayList manufacturersList = new ArrayList();
        for (Iterator iter = data.keySet().iterator(); iter.hasNext();) {
            manufacturersList.add((String) iter.next());
        }
        return manufacturersList;
    }

    public ArrayList getModels(String manufacturer, String brand) {
        ArrayList modelsList = new ArrayList();
        Manufacturer mfr = (Manufacturer) data.get(manufacturer);
        HashMap mfrBrands = (HashMap) mfr.getBrands();

        for (Iterator iter = ((ArrayList) mfrBrands.get(brand)).iterator(); iter.hasNext();) {
            modelsList.add((String) iter.next());
        }
        return modelsList;
    }
}
