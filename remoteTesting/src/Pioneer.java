import coppelia.*;

public class Pioneer {

	int clientID;
	IntW leftMotorHandle;
    IntW rightMotorHandle;
    
    IntW robotHandle;
    FloatWA robotPosition;
   
    FloatWA Orientation;
    remoteApi vrep;
		public Pioneer(int ID) {
			clientID = ID;
			leftMotorHandle = new IntW(1);
	        rightMotorHandle = new IntW(1);
	        robotHandle = new IntW(1);
	        robotPosition =new FloatWA(1);
	        Orientation = new FloatWA(1);
	        vrep = new remoteApi();
	        //Motor Handles
	        vrep.simxGetObjectHandle(clientID,"Pioneer_p3dx_leftMotor", leftMotorHandle, vrep.simx_opmode_blocking);
	        vrep.simxGetObjectHandle(clientID,"Pioneer_p3dx_rightMotor", rightMotorHandle, vrep.simx_opmode_blocking);
	        //Robot Handle
	        vrep.simxGetObjectHandle(clientID,"Pioneer_p3dx", robotHandle, vrep.simx_opmode_blocking);
	        //Robot Position
	        vrep.simxGetObjectPosition(clientID, robotHandle.getValue(), -1, robotPosition,vrep.simx_opmode_blocking);
	        //Get Robot Orientation
	        vrep.simxGetObjectOrientation(clientID, robotHandle.getValue(), -1, Orientation, vrep.simx_opmode_blocking);
		}
		
		//moves right motor
		public void moveRightMotor(float value) {
			vrep.simxSetJointTargetVelocity(clientID,rightMotorHandle.getValue(), value, vrep.simx_opmode_oneshot);
		} 
		//moves left motor
		public void moveLeftMotor(float value) {
			vrep.simxSetJointTargetVelocity(clientID,leftMotorHandle.getValue(),  value, vrep.simx_opmode_oneshot);
		}
		
		
		public void updatePosition() {
			vrep.simxGetObjectOrientation(clientID, robotHandle.getValue(), -1, Orientation, vrep.simx_opmode_blocking);
    			vrep.simxGetObjectPosition(clientID, robotHandle.getValue(), -1, robotPosition,vrep.simx_opmode_blocking);
		}
		
		
		public void rotateToPoint(int x, int y) {
			boolean stop = false;
			updatePosition();
	        double angle = Math.atan2(y - robotPosition.getArray()[1], x - robotPosition.getArray()[0]);
			
			while (stop == false) {
				updatePosition();
				if( angle - .025 < Orientation.getArray()[2]  && Orientation.getArray()[2] < angle + .025) {
        			//Stop Rotation
				moveRightMotor(0f);
        			stop = true;        			
        			} else {
        			moveRightMotor(.5f);
        			}
				
			}
			
			goToPoint(x,y);
			
			
		}
		
		
		public void goToPoint(int x, int y) {
			boolean stop = false;
			while(stop == false) {
			updatePosition();
					if( distance(x,y,robotPosition.getArray()[0], robotPosition.getArray()[1]) < .25 ) {
						moveRightMotor(0f);
						moveLeftMotor(0f);
						stop = true;
		     		} else {
		     			moveRightMotor(.5f);
						moveLeftMotor(.5f);
		     			
		     		}
			}
		}
		
		
		public static float distance(int x1, int y1, float x2, float y2) {
			
			float c1 = x2-x1;
			float c2 = y2-y1;
			
			float c3 = c1*c1;
			float c4 = c2*c2;
			
			float c5 = c3 + c4;
			
			float c6 = (float)Math.sqrt(c5);
			
			
			
			return c6;
		}
}
