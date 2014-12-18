package gui;

import middle.Statement;
import middle.InputBuffer;

import java.awt.*;
import java.util.Timer;
import java.util.Timer

/**
 * Created by ksh on 2014-05-24.
 */
class ElevatorPanel extends Panel {
    public final int WIDTH = 46;
    public final int HEIGHT = 53;
    private int maxHeight;
    private int x, y;
    private int doorGap;
    private Statement elevator;
    private InputBuffer inputBuffer;
    private int speed;
    private int doorSpeed=4;
    private int middleX;

    public ElevatorPanel( int y, int maxHeight) {
        this.x = 2;
        this.y = y + 2;
        this.maxHeight = maxHeight;
        setBackground(Color.GRAY);
        setBounds(x, y, WIDTH, HEIGHT);
        setLayout(null);

        inputBuffer = InputBuffer.getInstance();

        Statement = Statement.getInstance();
        speed = Statement.SPEED;
        Timer timer = new Timer();
        Move move = new Move();
        timer.schedule(move, 0, 34);

        middleX = (x+WIDTH)/2;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Image image = createImage(getWidth(),getHeight());
        Graphics imageG = image.getGraphics();

        imageG.setColor(Color.BLACK);

        imageG.drawLine(middleX, 0, middleX, HEIGHT);

        imageG.setColor(Color.WHITE);
        imageG.fillRect(middleX -doorGap/2,0,doorGap,HEIGHT);
        g.drawImage(image, 0, 0, this);
    }

    @Override
    public void update(Graphics g) {
        super.update(g);
    }

    public boolean isDoorClose(){
        if(doorGap == 0){
            return true;
        }
        return false;
    }
    public void closeDoor(){
        doorGap =0;
    }

    class Move extends TimerTask {
        private void move() {
            Statement.ServiceState serviceState = Statement.getServiceState();
            Statement.MoveState moveState = Statement.getMoveState();
            if(isDoorClose() && serviceState == Statement.ServiceState.SERVICE){
                if (moveState == Statement.MoveState.UP) {
                    y -= speed;
                } else if (moveState == Statement.MoveState.DOWN) {
                    y += speed;
                }
            }else if(serviceState == Statement.ServiceState.PAUSE){
                Statement.DoorState doorState = Statement.getCurrentDoorState();
                if(doorState == Statement.DoorState.OPENING){
                    doorGap+=doorSpeed;
                }else if(doorState == Statement.DoorState.CLOSEING){
                    doorGap-=doorSpeed;
                }else if(doorState == Statement.DoorState.CLOSE){
                    closeDoor();
                }
            }else {
                closeDoor();
            }
            repaint();
        }
        private void checkServiceState(){
            Statement.ServiceState serviceState = Statement.getServiceState();
            if(serviceState == Statement.ServiceState.PAUSE) {
                Statement.setMoveState(Statement.MoveState.NO_MOVE);
                Statement.setServiceState(Statement.ServiceState.PAUSE);
                int currentFloor = Statement.getCurrentFloor();
                inputBuffer.removeSelectionFloorInElevator(currentFloor);
                inputBuffer.removeSelectionFloor(currentFloor);
            }
        }

        private void checkCurrentFloor() {
            if (y % (HEIGHT + 4) < 5) {
                int currentFloor = maxHeight / (HEIGHT + 4) - y / (HEIGHT + 4);
                Statement.setCurrentFloor(currentFloor);
            }
        }

        @Override
        public void run() {
            move();
            checkCurrentFloor();
            checkServiceState();
            setLocation(x, y);
        }
    }
}