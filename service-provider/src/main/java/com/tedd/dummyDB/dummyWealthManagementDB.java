package com.tedd.dummyDB;

import com.tedd.Custodian;
import com.tedd.Security;
import com.tedd.SecurityType;

import java.util.ArrayList;
import java.util.List;

public class dummyWealthManagementDB {
    public static List<Custodian> getCustodiansFromTempDb() {
        return new ArrayList<Custodian>() {
            {
                add(Custodian.newBuilder().setCustodianID(1).setCustodianName("JP Morgan Chase").setCountry("USA").setRating("A+").build());
                add(Custodian.newBuilder().setCustodianID(2).setCustodianName("UBS").setCountry("Switzerland").setRating("A").build());
                add(Custodian.newBuilder().setCustodianID(3).setCustodianName("Credit Suisse").setCountry("Switzerland").setRating("A-").build());
                add(Custodian.newBuilder().setCustodianID(4).setCustodianName("HSBC").setCountry("UK").setRating("A").build());
                add(Custodian.newBuilder().setCustodianID(5).setCustodianName("BNP Paribas").setCountry("France").setRating("A").build());
            }
        };
    }

    public static List<Security> getSecuritiesFromTempDb() {
        return new ArrayList<Security>() {
            {
                add(Security.newBuilder().setSecurityID(1).setCustodianID(1).setType(SecurityType.STOCK).setSymbol("APPL").setSecurityName("Apple Inc.").setPrice(178.72f).setQuantity(500).build());
                add(Security.newBuilder().setSecurityID(2).setCustodianID(1).setType(SecurityType.STOCK).setSymbol("MSFT").setSecurityName("Microsoft Corporation").setPrice(334.55f).setQuantity(300).build());
                add(Security.newBuilder().setSecurityID(3).setCustodianID(2).setType(SecurityType.BOND).setSymbol("US10Y").setSecurityName("US 10-Year Treasury").setPrice(98.75f).setQuantity(1000).build());
                add(Security.newBuilder().setSecurityID(4).setCustodianID(3).setType(SecurityType.ETF).setSymbol("VTI").setSecurityName("Vanguard Total Stock Market ETF").setPrice(238.42f).setQuantity(200).build());
                add(Security.newBuilder().setSecurityID(5).setCustodianID(3).setType(SecurityType.STOCK).setSymbol("AMZN").setSecurityName("Amazon.com Inc.").setPrice(140.23f).setQuantity(150).build());
                add(Security.newBuilder().setSecurityID(6).setCustodianID(4).setType(SecurityType.BOND).setSymbol("DE10Y").setSecurityName("German 10-Year Bond").setPrice(99.45f).setQuantity(800).build());
                add(Security.newBuilder().setSecurityID(7).setCustodianID(5).setType(SecurityType.ETF).setSymbol("VXUS").setSecurityName("Vanguard Total International Stock ETF").setPrice(58.67f).setQuantity(400).build());
                add(Security.newBuilder().setSecurityID(8).setCustodianID(5).setType(SecurityType.BOND).setSymbol("GOOGL").setSecurityName("Alphabet Inc.").setPrice(141.18f).setQuantity(250).build());
            }
        };
    }
}