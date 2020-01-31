package com.luki.dmn.executor;

import org.camunda.bpm.dmn.engine.DmnDecision;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.dmn.engine.DmnEngineConfiguration;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Decider {
    public Decider() throws IOException {
        //this.init();
        //this.initFromStr("<?xml version=\"1.0\" encoding=\"UTF-8\"?><definitions xmlns=\"http://www.omg.org/spec/DMN/20151101/dmn.xsd\" id=\"definitions\" name=\"definitions\" namespace=\"http://camunda.org/schema/1.0/dmn\">    <decision id=\"decision\" name=\"Dish\">        <decisionTable id=\"decisionTable\">            <input id=\"input1\" label=\"Season\">                <inputExpression id=\"inputExpression1\" typeRef=\"string\">        <text>season</text>                </inputExpression>            </input>            <input id=\"InputClause_0hmkumv\" label=\"How many guests\">                <inputExpression id=\"LiteralExpression_0m7s53h\" typeRef=\"integer\">        <text>guestCount</text>                </inputExpression>            </input>            <output id=\"output1\" label=\"Dish\" name=\"desiredDish\" typeRef=\"string\" />            <rule id=\"row-950612891-1\">                <inputEntry id=\"UnaryTests_0c1o054\">        <text><![CDATA[\"Fall\"]]></text>                </inputEntry>                <inputEntry id=\"UnaryTests_1lod0sz\">        <text><![CDATA[<= 8]]></text>                </inputEntry>                <outputEntry id=\"LiteralExpression_065u3ym\">        <text><![CDATA[\"Spareribs\"]]></text>                </outputEntry>            </rule>            <rule id=\"row-950612891-2\">                <inputEntry id=\"UnaryTests_0u1z4ho\">        <text><![CDATA[\"Winter\"]]></text>                </inputEntry>                <inputEntry id=\"UnaryTests_1euytqf\">        <text><![CDATA[<= 8]]></text>                </inputEntry>                <outputEntry id=\"LiteralExpression_198frve\">        <text><![CDATA[\"Roastbeef\"]]></text>                </outputEntry>            </rule>            <rule id=\"row-950612891-3\">                <inputEntry id=\"UnaryTests_1vn9t5c\">        <text><![CDATA[\"Spring\"]]></text>                </inputEntry>                <inputEntry id=\"UnaryTests_1bbbmvu\">        <text><![CDATA[<= 4]]></text>                </inputEntry>                <outputEntry id=\"LiteralExpression_1bewepn\">        <text><![CDATA[\"Dry Aged Gourmet Steak\"]]></text>                </outputEntry>            </rule>            <rule id=\"row-950612891-4\">                <description>Save money</description>                <inputEntry id=\"UnaryTests_0ogofox\">        <text><![CDATA[\"Spring\"]]></text>                </inputEntry>                <inputEntry id=\"UnaryTests_0c60gjz\">        <text>[5..8]</text>                </inputEntry>                <outputEntry id=\"LiteralExpression_1lahvj7\">        <text><![CDATA[\"Steak\"]]></text>                </outputEntry>            </rule>            <rule id=\"row-950612891-5\">                <description>Less effort</description>                <inputEntry id=\"UnaryTests_1774yme\">        <text><![CDATA[\"Fall\", \"Winter\", \"Spring\"]]></text>                </inputEntry>                <inputEntry id=\"UnaryTests_01rn17i\">        <text><![CDATA[> 8]]></text>                </inputEntry>                <outputEntry id=\"LiteralExpression_0jpd7hr\">        <text><![CDATA[\"Stew\"]]></text>                </outputEntry>            </rule>            <rule id=\"row-950612891-6\">                <description>Hey, why not!?</description>                <inputEntry id=\"UnaryTests_0ifdx8k\">        <text><![CDATA[\"Summer\"]]></text>                </inputEntry>                <inputEntry id=\"UnaryTests_0c8ym7l\">        <text></text>                </inputEntry>                <outputEntry id=\"LiteralExpression_08d4mb6\">        <text><![CDATA[\"Light Salad and a nice Steak\"]]></text>                </outputEntry>            </rule>        </decisionTable>    </decision></definitions>");
    }

    DmnDecision decision;
    DmnEngine dmnEngine;

    public void init() throws IOException {
        // create a new default DMN engine
        dmnEngine = DmnEngineConfiguration.createDefaultDmnEngineConfiguration().buildEngine();

        // parse decision from resource input stream
//        try (InputStream inputStream = Decider.class.getResourceAsStream("/dish-decision.dmn11.xml")) {
//            decision = dmnEngine.parseDecision("decision", inputStream);
//        }
        try (InputStream inputStream = Decider.class.getResourceAsStream("/HK.dmn")) {
            decision = dmnEngine.parseDecision("Decision_17d3ytd", inputStream);
        }
    }

    public Boolean initFromStr(String str){

        dmnEngine = DmnEngineConfiguration.createDefaultDmnEngineConfiguration().buildEngine();

        // parse decision from resource input stream
        try (InputStream inputStream = new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8))) {
            decision = dmnEngine.parseDecision("decision", inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public Boolean initFromResource(String resourceName){
        dmnEngine = DmnEngineConfiguration.createDefaultDmnEngineConfiguration().buildEngine();

        // parse decision from resource input stream
//        try (InputStream inputStream = Decider.class.getResourceAsStream("/dish-decision.dmn11.xml")) {
//            decision = dmnEngine.parseDecision("decision", inputStream);
//        }
        try (InputStream inputStream = Decider.class.getResourceAsStream(resourceName)) {
            decision = dmnEngine.parseDecision("decision", inputStream);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public Object decideFromTwoList(String[] variableNames, Object[] values){

        VariableMap variables = Variables.createVariables();

        for (int i = 0;i<variableNames.length;i++){
            variables.putValue(variableNames[i],values[i]);
        }

        DmnDecisionTableResult result = dmnEngine.evaluateDecisionTable(decision, variables);

        // print result
        Object singleResult = result.getSingleResult().getSingleEntry();
        System.out.println("Decision: " + singleResult);

        return singleResult;
    }

    public Object[] decideFromTwoListToArray(String[] variableNames, String[] output, Object[] values){

        VariableMap variables = Variables.createVariables();

        for (int i = 0;i<variableNames.length;i++){
            variables.putValue(variableNames[i],values[i]);
        }

        DmnDecisionTableResult result = dmnEngine.evaluateDecisionTable(decision, variables);

        // print result
        List<Object> list = new ArrayList<>();

        Map map = result.getSingleResult().getEntryMap();

        for(String o: output){
            list.add(map.get(o));
        }

        System.out.println("Decision: " + Arrays.toString(list.toArray()));

        return list.toArray();
    }


    public String decideHK(String... args){


        VariableMap variables = Variables
                .putValue("incorportion", args[0])
                .putValue("headquarter", args[1])
                .putValue("revenueFromChina", args[2])
                .putValue("isCurrent", args[3])
                .putValue("isRedChip", args[4])
                .putValue("iSO_Curr", args[5]);

        DmnDecisionTableResult result = dmnEngine.evaluateDecisionTable(decision, variables);

        // print result
        String singleResult = result.getSingleResult().getSingleEntry();
        System.out.println("Dish Decision:\n\tI would recommend to serve: " + singleResult);

        return singleResult;
    }

    public String decide(String season, String guestCount){
        // prepare variables for decision evaluation
        VariableMap variables = Variables
                .putValue("season", season)
                .putValue("guestCount", guestCount);


        // evaluate decision
        DmnDecisionTableResult result = dmnEngine.evaluateDecisionTable(decision, variables);

        // print result
        String desiredDish = result.getSingleResult().getSingleEntry();
        System.out.println("Dish Decision:\n\tI would recommend to serve: " + desiredDish);

        return desiredDish;
    }
}
