package process;

import middle.Statement;
import middle.InputBuffer;

import java.util.*;

/**
 * Created by ksh on 2014-05-24.
 */
/*
엘레베이터가 도착할 목표 위치를 계산하는 클래스
 */
public class Process {
    private InputBuffer inputBuffer;
    private Statement elevator;

    public Process() {
        inputBuffer = InputBuffer.getInstance();
        elevator = Statement.getInstance();
    }

    public void process() {
        Timer timer = new Timer();
        Job job = new Job();
        timer.schedule(job, 0, 34);
    }

    class Job extends TimerTask {
        private ArrayList<Integer> createTargetFloorList() {
            Statement.ServiceState serviceState = elevator.getServiceState();
            if (serviceState == Statement.ServiceState.STOP) {
                return null;
            }
            Statement.MoveState moveState = elevator.getMoveState();
            int currentFloor = elevator.getCurrentFloor();
            List<Integer> selectionFloorsInElevator = inputBuffer.getAllSelectionFloorInElevator();

            List<Integer> selectionFloorOutsideList = null;
            if (moveState == Statement.MoveState.UP) {
                selectionFloorOutsideList = inputBuffer.getAllSelectionFloorOutside("UP");
            } else if (moveState == Statement.MoveState.DOWN) {
                selectionFloorOutsideList = inputBuffer.getAllSelectionFloorOutside("DOWN");
            }else {
                selectionFloorOutsideList = inputBuffer.getAllSelectionFloorOutside();
            }

            Set<Integer> set = Collections.synchronizedSet(new HashSet<Integer>());
            set.addAll(selectionFloorsInElevator);
            if (selectionFloorOutsideList != null) {
                set.addAll(selectionFloorOutsideList);
            }

            ArrayList<Integer> result = new ArrayList<Integer>();
            ArrayList<Integer> temp = new ArrayList<Integer>(set);
            Collections.sort(temp);
            if (moveState == Statement.MoveState.UP) {
                for (int i = 0; i < temp.size(); i++) {
                    int targetFloor = temp.get(i);
                    if (currentFloor <= targetFloor) {
                        result.add(targetFloor);
                    }
                }
                for (int i = temp.size() - 1; i >= 0; i--) {
                    int targetFloor = temp.get(i);
                    if (!result.contains(targetFloor) && currentFloor > targetFloor) {
                        result.add(targetFloor);
                    }
                }
                if(inputBuffer.getAllSelectionFloorOutside("UP").isEmpty()){
                    selectionFloorOutsideList = inputBuffer.getAllSelectionFloorOutside("DOWN");
                    Collections.sort(selectionFloorOutsideList);
                    for (int i = 0; i < selectionFloorOutsideList.size(); i++) {
                        int targetFloor = selectionFloorOutsideList.get(i);
                        if (!result.contains(targetFloor) && targetFloor > currentFloor) {
                            result.add(0,targetFloor);
                        }
                    }
                }
            } else if (moveState == Statement.MoveState.DOWN) {
                for (int i = temp.size() - 1; i >= 0; i--) {
                    int targetFloor = temp.get(i);
                    if (currentFloor >= targetFloor) {
                        result.add(targetFloor);
                    }
                }
                for (int i = 0; i < temp.size(); i++) {
                    int targetFloor = temp.get(i);
                    if (!(result.contains(targetFloor)) && currentFloor < targetFloor) {
                        result.add(targetFloor);
                    }
                }
                if(inputBuffer.getAllSelectionFloorOutside("DOWN").isEmpty()){
                    selectionFloorOutsideList = inputBuffer.getAllSelectionFloorOutside("UP");
                    Collections.sort(selectionFloorOutsideList);
                    for (int i = selectionFloorOutsideList.size()-1; i >=0 ; i--) {
                        int targetFloor = selectionFloorOutsideList.get(i);
                        if (!result.contains(targetFloor) && targetFloor < currentFloor) {
                            result.add(0,targetFloor);
                        }
                    }
                }
            } else if (moveState == Statement.MoveState.NO_MOVE) {
                if (currentFloor == 1) {
                    for (int i = 0; i < temp.size(); i++) {
                        int targetFloor = temp.get(i);
                        result.add(targetFloor);
                    }
                }else {
                    for (int i = temp.size()-1; i >=0 ; i--) {
                        int targetFloor = temp.get(i);
                        result.add(targetFloor);
                    }
                }
            }

            return result;
        }

        @Override
        public void run() {
            ArrayList<Integer> targetFloors = createTargetFloorList();
            elevator.setTargetFloors(targetFloors);
        }
    }
}
