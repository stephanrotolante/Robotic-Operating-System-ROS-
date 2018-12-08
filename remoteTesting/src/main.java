import coppelia.*;

import java.util.concurrent.TimeUnit;

import java.util.Scanner;

import java.lang.Math;


public class main {

	public static void main(String[] args) throws InterruptedException {
		
		System.load("/Users/stephanrotolante/Downloads/V-REP_PRO_EDU_V3_4_0_Mac/programming/remoteApiBindings/java/lib/libremoteApiJava.dylib");
	
		System.out.println("Create waypoint enter X and Y Coordinates");
		Scanner scan = new Scanner(System.in);
		int x1 = scan.nextInt();
		int y1 = scan.nextInt();
		
        remoteApi vrep = new remoteApi();
        vrep.simxFinish(-1); // just in case, close all opened connections
        int clientID = vrep.simxStart("127.0.0.1",19997,true,true,5000,5);
        if (clientID!=-1)
        {  
            vrep.simxStartSimulation(clientID, vrep.simx_opmode_oneshot);
    
           Pioneer pioneer = new Pioneer(clientID);
     
            while (clientID != -1) {
            	pioneer.updatePosition();
            	
            	
            	pioneer.rotateToPoint(x1, y1);
            
           	} 
            	
            	
     
        }
	} //End Main
	
	
	

}//End Class
