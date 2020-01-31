package com.luki.dmn.executor;

import org.camunda.bpm.dmn.engine.DmnDecision;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.dmn.engine.DmnEngineConfiguration;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;

import java.io.IOException;
import java.io.InputStream;

public class Main {


    public static void printUsage(String errorMessage, int exitCode) {
        System.err.println("Error: " + errorMessage);
        System.err.println("Usage: java -jar DishDecider.jar SEASON GUEST_COUNT\n\n\tSEASON: the current season (Spring, Summer, Fall or Winter)\n\tGUEST_COUNT: number of guest to expect");
        System.exit(exitCode);
    }

    public static void main2(String[] args) throws IOException {
        Decider decider = new Decider();
        //decider.initFromStr("<?xml version=\"1.0\" encoding=\"UTF-8\"?><definitions xmlns=\"http://www.omg.org/spec/DMN/20151101/dmn.xsd\" id=\"definitions\" name=\"definitions\" namespace=\"http://camunda.org/schema/1.0/dmn\">    <decision id=\"decision\" name=\"Dish\">        <decisionTable id=\"decisionTable\">            <input id=\"input1\" label=\"Season\">                <inputExpression id=\"inputExpression1\" typeRef=\"string\">        <text>season</text>                </inputExpression>            </input>            <input id=\"InputClause_0hmkumv\" label=\"How many guests\">                <inputExpression id=\"LiteralExpression_0m7s53h\" typeRef=\"integer\">        <text>guestCount</text>                </inputExpression>            </input>            <output id=\"output1\" label=\"Dish\" name=\"desiredDish\" typeRef=\"string\" />            <rule id=\"row-950612891-1\">                <inputEntry id=\"UnaryTests_0c1o054\">        <text><![CDATA[\"Fall\"]]></text>                </inputEntry>                <inputEntry id=\"UnaryTests_1lod0sz\">        <text><![CDATA[<= 8]]></text>                </inputEntry>                <outputEntry id=\"LiteralExpression_065u3ym\">        <text><![CDATA[\"Spareribs\"]]></text>                </outputEntry>            </rule>            <rule id=\"row-950612891-2\">                <inputEntry id=\"UnaryTests_0u1z4ho\">        <text><![CDATA[\"Winter\"]]></text>                </inputEntry>                <inputEntry id=\"UnaryTests_1euytqf\">        <text><![CDATA[<= 8]]></text>                </inputEntry>                <outputEntry id=\"LiteralExpression_198frve\">        <text><![CDATA[\"Roastbeef\"]]></text>                </outputEntry>            </rule>            <rule id=\"row-950612891-3\">                <inputEntry id=\"UnaryTests_1vn9t5c\">        <text><![CDATA[\"Spring\"]]></text>                </inputEntry>                <inputEntry id=\"UnaryTests_1bbbmvu\">        <text><![CDATA[<= 4]]></text>                </inputEntry>                <outputEntry id=\"LiteralExpression_1bewepn\">        <text><![CDATA[\"Dry Aged Gourmet Steak\"]]></text>                </outputEntry>            </rule>            <rule id=\"row-950612891-4\">                <description>Save money</description>                <inputEntry id=\"UnaryTests_0ogofox\">        <text><![CDATA[\"Spring\"]]></text>                </inputEntry>                <inputEntry id=\"UnaryTests_0c60gjz\">        <text>[5..8]</text>                </inputEntry>                <outputEntry id=\"LiteralExpression_1lahvj7\">        <text><![CDATA[\"Steak\"]]></text>                </outputEntry>            </rule>            <rule id=\"row-950612891-5\">                <description>Less effort</description>                <inputEntry id=\"UnaryTests_1774yme\">        <text><![CDATA[\"Fall\", \"Winter\", \"Spring\"]]></text>                </inputEntry>                <inputEntry id=\"UnaryTests_01rn17i\">        <text><![CDATA[> 8]]></text>                </inputEntry>                <outputEntry id=\"LiteralExpression_0jpd7hr\">        <text><![CDATA[\"Stew\"]]></text>                </outputEntry>            </rule>            <rule id=\"row-950612891-6\">                <description>Hey, why not!?</description>                <inputEntry id=\"UnaryTests_0ifdx8k\">        <text><![CDATA[\"Summer\"]]></text>                </inputEntry>                <inputEntry id=\"UnaryTests_0c8ym7l\">        <text></text>                </inputEntry>                <outputEntry id=\"LiteralExpression_08d4mb6\">        <text><![CDATA[\"Light Salad and a nice Steak\"]]></text>                </outputEntry>            </rule>        </decisionTable>    </decision></definitions>");
        decider.init();
        String out = decider.decideHK("US", "HKG", "0.4", "true", "false", "HKD");


    }

    public static void main(String[] args) throws IOException {
        Decider decider = new Decider();
        //decider.init();
        decider.initFromResource("/diagram_3y.dmn");

        String[] var = new String[] {"region", "mIC", "mIC_desc"};
        String[] values = new String[] {"AR", "XBUE", ""};
        String[] outVar = new String[] {"Elegible-market","output2"};
//  outputRowData[outputIndex++] = decider.decideHK(incorportion, headquarter,
//revenueFromChina, isCurrent, isRedChip, iSO_Curr);


        Object[] outputArray = decider.decideFromTwoListToArray(var,outVar, values);

        System.out.println(outputArray);

    }

}
