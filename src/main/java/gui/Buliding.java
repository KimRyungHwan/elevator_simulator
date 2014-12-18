package gui;

import middle.InputBuffer;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by ksh on 2014-05-24.
 */
/*
타이머를 통해서 일정 시간 마다 건물을 그리는 클래스
 */
class MainPanel extends Panel{
    private BuildingPanel buildingPanel;
    private ElevatorBtnPanel elevatorBtnPanel;
    private ArrayList<FloorBtnPanel> floorBtnPanels;

    private int numOfFloor;
    private String ID;

    public MainPanel(String ID, int numOfFloor, int x, int y){
        this.ID = ID;
        this.numOfFloor = numOfFloor;

        int pointX=0,pointY=0;
        floorBtnPanels = new ArrayList<FloorBtnPanel>();
        for(int i= 1;i<=numOfFloor; i++){
            FloorBtnPanel panel = new FloorBtnPanel(ID+"_"+i+"f",pointX,FloorBtnPanel.HEIGHT*(numOfFloor-i));
            floorBtnPanels.add(panel);
            add(panel);
        }
        pointX += FloorBtnPanel.WIDTH;

        buildingPanel = new BuildingPanel(ID,numOfFloor,pointX,0);
        add(buildingPanel);
        pointY += buildingPanel.getHeight();
        pointX += buildingPanel.WIDTH;

        elevatorBtnPanel = new ElevatorBtnPanel(ID,pointX,0,numOfFloor);
        add(elevatorBtnPanel);
        pointX += elevatorBtnPanel.getWidth();


        setLayout(null);
        setBounds(x,y,pointX,pointY);
        Timer timer = new Timer();
        CheckBtn checkBtn = new CheckBtn();
        timer.schedule(checkBtn,0,34);
    }
    class CheckBtn extends TimerTask{

        @Override
        public void run() {
            InputBuffer inputBuffer = InputBuffer.getInstance();
            List<Integer> selectionFloorsInElevator= inputBuffer.getAllSelectionFloorInElevator();
            elevatorBtnPanel.toggleBtns(selectionFloorsInElevator);
            //=======================
            for(FloorBtnPanel floorBtnPanel : floorBtnPanels){
                floorBtnPanel.toggleBtn();
            }

            ArrayList<Integer> selectionFloors = inputBuffer.getAllSelectionFloorOutside();
            Map<Integer,String> selectionFloorsHashMap = inputBuffer.getAllSelectionFloorHashMap();
            for (Integer selectionFloor: selectionFloors){
                FloorBtnPanel floorBtnPanel=floorBtnPanels.get(selectionFloor-1);
                String upDown = selectionFloorsHashMap.get(selectionFloor);
                floorBtnPanel.toggleBtn(upDown);
            }
        }
    }
}
