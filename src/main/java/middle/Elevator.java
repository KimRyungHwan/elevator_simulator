package middle;

import java.util.*;

/**
 * Created by ksh on 2014-05-24.
 */
/*
엘레베이터의 상태를 저장하는 클래스
 */
public class Elevator {
    private static Elevator instance = null;
    public final int SPEED = 4;




    public enum DoorState {OPEN, OPENING, CLOSE, CLOSEING}

    public enum MoveState {UP, DOWN, NO_MOVE}

    public enum ServiceState {SERVICE, PAUSE, STOP}

    private DoorState currentDoorState = DoorState.CLOSE;
    private ServiceState currentServiceState = ServiceState.PAUSE;


    private MoveState currentMoveState = MoveState.NO_MOVE;
    private List<Integer> targetFloors = Collections.synchronizedList(new ArrayList<Integer>());
    private int currentFloor = 8;


    private Elevator() {
        Timer timer = new Timer();
        CheckState checkState = new CheckState();
        timer.schedule(checkState, 0, 34);
    }

    public static Elevator getInstance() {
        if (instance == null) {
            synchronized (InputBuffer.class) {
                if (instance == null) {
                    instance = new Elevator();
                }
            }
        }
        return instance;
    }

    //getters
    public DoorState getCurrentDoorState() {
        return currentDoorState;
    }




    public MoveState getMoveState() {
        return currentMoveState;
    }

    public ServiceState getServiceState() {
        return currentServiceState;
    }

    public int getCurrentFloor() { return currentFloor; }
    //setters

    public void setServiceState(ServiceState currentServiceState) {
        this.currentServiceState = currentServiceState;
    }

    public void setMoveState(MoveState moveState) {
        currentMoveState = moveState;
    }




    public void setCurrentFloor(int floor) {
        currentFloor = floor;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setTargetFloors(ArrayList<Integer> targetFloors) {
        if (targetFloors != null) {
            if(!targetFloors.isEmpty()){
                this.targetFloors = targetFloors;
            }
        }
    }

    //inner class

    class CheckState extends TimerTask {
        private void changeState() {
            //Check the target floor and determine the movement
            if (!targetFloors.isEmpty()) {
                currentServiceState = ServiceState.SERVICE;
                if(currentMoveState == MoveState.NO_MOVE){
                    //가까운 ?????
                   // currentMoveState = MoveState.UP;
                }
                if (targetFloors.get(0) > currentFloor) {
                    currentMoveState = MoveState.UP;
                } else if (targetFloors.get(0) < currentFloor) {
                    currentMoveState = MoveState.DOWN;
                } else if (targetFloors.get(0) == currentFloor){
                    if (currentServiceState != ServiceState.PAUSE) {
                        currentServiceState = ServiceState.PAUSE;
                        //currentMoveState = MoveState.NO_MOVE;

                        targetFloors.remove(0);
                        try {
                            int interval = 300;
                            //Open and close the door (only statement changing)
                            currentDoorState = DoorState.OPENING;
                            Thread.sleep(interval);
                            currentDoorState = DoorState.OPEN;
                            Thread.sleep(interval);
                            currentDoorState = DoorState.CLOSEING;
                            Thread.sleep(interval);
                            currentDoorState = DoorState.CLOSE;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        currentServiceState = ServiceState.SERVICE;
                    }
                    if(currentFloor == 8 || currentFloor == 1){
                        currentMoveState = MoveState.NO_MOVE;
                    }
                }
            } else {
                currentMoveState = MoveState.NO_MOVE;
                currentServiceState = ServiceState.PAUSE;
                currentDoorState = DoorState.CLOSE;
            }
        }

        @Override
        public void run() {
            changeState();
        }
    }
}
