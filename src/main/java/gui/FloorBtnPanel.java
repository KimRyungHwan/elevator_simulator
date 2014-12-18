package gui;

import middle.InputBuffer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
* Created by ksh on 2014-05-24.
*/
class FloorBtnPanel extends Panel implements ActionListener {

    private int height = 57, width = 30;
    private int floorNum;
    private InputBuffer inputBuffer = InputBuffer.getInstance();
    private Button upBtn, downBtn;

    public FloorBtnPanel(int floorNum, int x, int floor) {
        this.floorNum = floorNum;
        setBounds(x, height * (floor-1), width, height);
        setBackground(Color.GRAY);
        setLayout(null);

        upBtn = new Button("△");
        upBtn.addActionListener(this);
        upBtn.setBounds(6, 7, 20, 20);
        upBtn.setVisible(true);
        add(upBtn);

        downBtn = new Button("▽");
        downBtn.addActionListener(this);
        downBtn.setBounds(6, 32, 20, 20);
        add(downBtn);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int floor = getFloor();
        String command = e.getActionCommand();
        if("△".equals(command)){
            inputBuffer.selectUpDown(floor, "UP");
            upBtn.setLabel("▲");
        }else if("▽".equals(command)){
            inputBuffer.selectUpDown(floor, "DOWN");
            downBtn.setLabel("▼");
        }

    }

    private int getFloor() {
        return floorNum;


    }

    public void toggleBtn(String upDown){
        upBtn.setLabel("△");
        downBtn.setLabel("▽");
        if(upDown.equals("UP")){
            upBtn.setLabel("▲");
        }else if(upDown.equals("DOWN")){
            downBtn.setLabel("▼");
        }
    }
    public void toggleBtn(){
        upBtn.setLabel("△");
        downBtn.setLabel("▽");
    }
}
