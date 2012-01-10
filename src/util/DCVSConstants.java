package util;

public interface DCVSConstants {
	String REPOSITORY_ONE = "localhost";
	String REPOSITORY_TWO = "localhost";
	String REPOSITORY_THREE = "localhost";
	
    Long REPOSITORY_SIZE = new Long(5000000);
    
    String REPOSITORY_USER_NAME = "user";
    String REPOSITORY_PWD = "pwd";
    
    String REPOSITORY_FILE_LOC = "DCVS";
    
    String CHECKIN_COMMAND = "checkin";
    String CHECKOUT_COMMAND = "checkout";
    String SHOW_ALL_COMMAND = "showall";
    Integer REPOSITORY_SEND_PORT_NUMBER = 12000;
    Integer MANAGER_CHECKIN_PORT_NUMBER = 17000;
    Integer MANAGER_CHECKOUT_PORT_NUMBER = 17001;
    Integer MANAGER_SHOWALL_PORT_NUMBER = 17002;
    Integer REPOSITORY_STORE_PORT_NUMBER = 12001;
    
    

}
