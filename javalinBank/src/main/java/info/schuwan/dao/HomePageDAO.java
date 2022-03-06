package info.schuwan.dao;

public class HomePageDAO {

    String mainMessage = "This is an informtion page about the banking system!\n\n\n" +
            "Please use a REST API client to connect and process requests\n\n" +
            "- You need to setup your User Login info as follows:\n" +
            "   1- POST request to http://localhost:8080/auth/login\n" +
            "   2- choose type JSON and it should look like this:\n" +
            "       {\n" +
            "\t\"username\" : \"august.duet\",\n" +
            "\t\"password\" : \"p@$$w0rd123\"\n" +
            "}" +
            "\n\n\n\n" +
            "- If you want to view your transactions you should do the following:\n" +
            "   1- GET request to http://localhost:8080/banking\n" +
            "   2- choose Auth type Beare with a TOKEN Response to body attribute" +
            "and edit it as follows\n" +
            "               - Request: POST User Login\n" +
            "               - Filter: $.authToken\n" +
            "               -Trigger Behavior: When expired-resend\n" +
            "               -Max age: 3600\n\n\n\n" +
            "- If you want to add a new transaction to your account do the following:\n" +
            "   1- request to http://localhost:8080/banking\n" +
            "   2- choose type JSON and it should look like this:\n" +
            "{\n" +
            "\t\"greetingText\": {\n" +
            "\t\t\t\t\t\"id\":\"13\",\n" +
            "\t\t\t\t\t\"date\":\"01/29/22\",\n" +
            "\t\t\t\t\t\"type\":\"credit\",\n" +
            "\t\t\t\t\t\"description\":\"ATM-Jericho Tpke-NassauCounty-NY- Deposit\",\n" +
            "\t\t\t\t\t\"amount\":\"2000\"\n" +
            "\t}\n" +
            "}\n\n\n" +
            "   3- choose Auth type Beare with a TOKEN Response to body attribute\" +\n" +
            "                 and edit it as follows\" +\n" +
            "                - Request: POST User Login\" +\n" +
            "                - Filter: $.authToken\" +\n" +
            "                -Trigger Behavior: When expired-resend\" +\n" +
            "                -Max age: 3600\n" +")\n\n" +
            "- If you are a bank employee and want to add a new account, do the following:\n" +
            "   1- request to http://localhost:8080/admin/banking\n" +
            "   2- choose type JSON and it should look like this:\n" +
            "{\n" +
            "\t\"greetingText\": {\n" +
            "\t\t\t\t\t\"id\":\"13\",\n" +
            "\t\t\t\t\t\"username\":\"john.doe\",\n" +
            "\t\t\t\t\t\"password\":\"\"p@$$w0rd123\",\n" +
            "\t\t\t\t\t\"roles\":[\"USER\"],\n" +
            "\t\t\t\t\t\"creditscore\":\"750\"\n" +
            "\t\t\t\t\t\"email\":\"john@revature.com\",\n" +
            "\t\t\t\t\t\"accounts\":[\"CHECKING1\"],\n" +
            "\t}\n" +
            "}\n\n\n" +
            "   3- choose Auth type Beare with a TOKEN Response to body attribute\" +\n" +
            "                 and edit it as follows\" +\n" +
            "                - Request: POST User Login\" +\n" +
            "                - Filter: $.authToken\" +\n" +
            "                -Trigger Behavior: When expired-resend\" +\n" +
            "                -Max age: 3600\n" +")";

    public String getMainMessage() {
        return mainMessage;
    }
}
