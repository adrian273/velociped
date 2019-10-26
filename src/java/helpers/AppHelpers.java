/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * @author: Adrian
 */
package helpers;

import java.text.DecimalFormat;

/**
 *
 * @author adrian
 */
public class AppHelpers {
    
    public AppHelpers() {
        
    }
    
    /**
     * format : $0.000
     * @param price
     * @return 
     */
    public String priceFormat(String price) {
        DecimalFormat df = new DecimalFormat("#,###");
        return "$" + df.format(Double.parseDouble(price));
    }
    
}
