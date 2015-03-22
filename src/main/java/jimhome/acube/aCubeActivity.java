package jimhome.acube;

import android.app.Activity;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;
import android.media.ToneGenerator;


public  class aCubeActivity extends Activity implements View.OnClickListener, SensorEventListener{

    private SensorManager mSensorManager;
    private Sensor mGyroSensor;
    private  Sensor myAccellor;
    private Sensor myLight;
    private Vibrator vibrator;
    long lastUpdate = System.currentTimeMillis();
    long lastShake = 0;
    float last_x = 0;
    float last_y = 0;
    float last_z = 0;

    ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_SYSTEM,100);


    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something if sensor accuracy changes.
    }


    @Override
    public final void onSensorChanged(SensorEvent event) {
        final int type = event.sensor.getType();
        float y = 0;
        float x = 0;
        float z = 0;
        int leftMotorSpeed = 0;
        int righMotorSpeed = 0;
        String NewEventFlag = new String();
        if (type != Sensor.TYPE_ACCELEROMETER)
            return;
        if (event.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE)
            return;
        float Gx = event.values[0];
        float Gy = event.values[1];
        float Gz = event.values[2];
        boolean underMark = false;
        boolean overMark = false;
        long curTime = System.currentTimeMillis();
        // only allow one update every 100ms.
        if (cbxShake.isChecked() == true) {
            if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;
                x = Gx;
                y = Gy;
                z = Gz;
                float speed = Math.abs(x + y + z - last_x - last_y - last_z);
                if (speed > 25) {
                    //tvLeft.setText("shake: " + speed);
                    if (ledCube.busy != true) {
                        ledCube.RandomLedsRandomColors();
                        lastShake = curTime;
                    }
                }
                last_x = x;
                last_y = y;
                last_z = z;
            }
        }
       // tvLeft.setText("x:"+ String.format("%.1f", Gx) +"  y:"+ String.format("%.1f", Gy) + " z:"+ String.format("%.1f", Gz));
    }

    protected void onResume(){
        super.onResume();
        mSensorManager.registerListener(this, myAccellor, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, myLight, SensorManager.SENSOR_DELAY_NORMAL);
    }


    @Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//Bluetooth.write("Q");}//Stop streaming
		super.onBackPressed();
	}

    LedCubeTicTacToe ledCube = new LedCubeTicTacToe();

    static int upLeft = 0;

    String strReadData  = new String();


	Button btnCLEAR;
    ImageButton btnYplus;
    ImageButton btnYminus;
    ImageButton btnXplus;
    ImageButton btnXminus;
    ImageButton btnZplus;
    ImageButton btnZminus;
    Button btnSetBlue;

    TextView tvLeft;

	Button bConnect, bDisconnect;
    CheckBox cbxShake;
    CheckBox cbxSound;

	Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch(msg.what){
			case aBluetooth.SUCCESS_CONNECT:
				aBluetooth.connectedThread = new aBluetooth.ConnectedThread((BluetoothSocket)msg.obj);
				Toast.makeText(getApplicationContext(), "Connected!", 0).show();
				String s = "successfully connected";
				aBluetooth.connectedThread.start();
				break;
			case aBluetooth.MESSAGE_READ:
				byte[] readBuf = (byte[]) msg.obj;
				String strIncom = new String(readBuf, 0, msg.arg1);
                strReadData = strReadData+strIncom;
               // Log.d("strIncom", strReadData);
               // tvLeft.setText(strReadData);
                int _CloseBracketPos = strReadData.indexOf('}');
                if ( _CloseBracketPos>= 0 ) {
                    String strMessage = new String();
                    strMessage = strReadData.substring(0,_CloseBracketPos);
                   // Log.d("strMessage", strMessage);
                    strReadData = "";
                    tvLeft.setText(strMessage);
                }
				break;
			}
		}

		public boolean isFloatNumber(String num){
			//Log.d("checkfloatNum", num);
			try{
				Double.parseDouble(num);
			} catch(NumberFormatException nfe) {
				return false;
			}
			return true;
		}

	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//Hide title
		this.getWindow().setFlags(WindowManager.LayoutParams.
				FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//Hide Status bar
		setContentView(R.layout.activity_acube);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mGyroSensor=mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        myAccellor= mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        myLight= mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		init();
		InitControls();
	}

	void init(){
		aBluetooth.gethandler(mHandler);
	}

	void InitControls(){
        cbxShake = (CheckBox)findViewById(R.id.cbxShake);
        cbxShake.setOnClickListener(this);
        cbxSound = (CheckBox)findViewById(R.id.cbxSound);
        cbxSound.setOnClickListener(this);
		bConnect = (Button)findViewById(R.id.bConnect);
		bConnect.setOnClickListener(this);
        btnCLEAR = (Button)findViewById(R.id.btnCLEAR);
        btnCLEAR.setOnClickListener(this);
        btnYplus = (ImageButton)findViewById(R.id.btnYplus); //
        btnYplus.setOnClickListener(this);
        btnYminus = (ImageButton)findViewById(R.id.btnYminus);//
        btnYminus.setOnClickListener(this);
        btnXplus = (ImageButton)findViewById(R.id.btnXplus);
        btnXplus.setOnClickListener(this);
        btnXminus = (ImageButton)findViewById(R.id.btnXminus);
        btnXminus.setOnClickListener(this);

        btnZplus = (ImageButton)findViewById(R.id.btnZplus); //btnZplus
        btnZplus.setOnClickListener(this);
        btnZminus = (ImageButton)findViewById(R.id.btnZminus);//btnZminus
        btnZminus.setOnClickListener(this);
        btnSetBlue  = (Button)findViewById(R.id.btnSetBlue);
        btnSetBlue.setOnClickListener(this);

        tvLeft = (TextView)findViewById(R.id.tvLeft);
		//init toggleButton
	}

    public void PlaySound() {
        if (!cbxSound.isChecked())
             return;
        toneG.startTone(ToneGenerator.TONE_SUP_BUSY, 200);
    }

	@Override
	public void onClick(View v) {
        // TODO Auto-generated method stub
        boolean PlayerWins = false;
        boolean MachineWins = false;
        switch (v.getId()) {
            case R.id.bConnect:
                if (aBluetooth.connectedThread != null) {
                    aBluetooth.disconnect();
                    bConnect.setText("CONNECT");
                   // bConnect.setBackgroundColor(Color.DKGRAY);
                }
                else {
                    startActivity(new Intent("android.intent.action.aBT"));
                    bConnect.setText("DISCONNECT");
                   // bConnect.setBackgroundColor(Color.GREEN);
                }
                break;
            case R.id.btnCLEAR:
                ledCube.AllOff();
                ledCube.PlayerWon = false;
                ledCube.MachineWon = false;
                ledCube.BlueToothWrite();
                ledCube.MoveTo(0,0,0);
                ledCube.BlueToothWrite();
                //ledCube.AnotherSpot.set(3,3,3);
                //ledCube.DrawLine(ledCube.home3D,ledCube.AnotherSpot,"blue");
                //ledCube.BlueToothWrite();
                //ledCube.WalkThisWayBasic();
                //ledCube.direction = "xup";
                //while (ledCube.direction != "stop")
                //    ledCube.Step();
                //ledCube.WalkThisWay();
                break;
            case R.id.btnYplus:
                if (ledCube.GameOver())
                    break;
                ledCube.Zplus();
                PlaySound();
                ledCube.BlueToothWrite();
                break;
            case R.id.btnYminus:
                if (ledCube.GameOver())
                    break;
                ledCube.Zminus();
                PlaySound();
                ledCube.BlueToothWrite();
                break;
            case R.id.btnXplus:
                if (ledCube.GameOver())
                    break;
                ledCube.Xplus();
                PlaySound();
                ledCube.BlueToothWrite();
                break;
            case R.id.btnXminus:
                if (ledCube.GameOver())
                    break;
                ledCube.Xminus();
                PlaySound();
                ledCube.BlueToothWrite();
                break;
            case R.id.btnZplus:
                if (ledCube.GameOver())
                    break;
                ledCube.Yplus();
                PlaySound();
                ledCube.BlueToothWrite();
                break;
            case R.id.btnZminus:
                if (ledCube.GameOver())
                    break;
                ledCube.Yminus();
                PlaySound();
                ledCube.BlueToothWrite();
                break;
            case R.id.btnSetBlue:
                tvLeft.setText("");
                if (ledCube.GameOver())
                    break;
                if (! ledCube.setTurnedOnByUser())
                    break;
                ledCube.BlueToothWrite();
                PlayerWins = ledCube.checkForWin();
                if (PlayerWins) {
                    //ledCube.AllOn("purple");
                    //tvLeft.setText("x:"+ String.format("%d", ledCube.winStart.x) +
                    //               "  y:"+String.format("%d", ledCube.winStart.y) +
                    //               " z:"+String.format("%d",  ledCube.winStart.z));
                    ledCube.AllOff();
                    ledCube.showWiningLine();
                    ledCube.BlueToothWrite();
                    toneG.startTone(ToneGenerator.TONE_CDMA_ANSWER, 2000);
                    break;
                }
                ledCube.checkForMachineWin();
                if (ledCube.canWin) {
                    tvLeft.setText("canWin");
                    ledCube.setTurnedOnByMachine();
                    ledCube.BlueToothWrite();
                    ledCube.checkForMachineWin();
                }
                ledCube.checkForWin();
                if (ledCube.needDefensiveMove) {
                    tvLeft.setText("DefensiveMov");
                    //tvLeft.setText("nextDefensiveMove x:" + String.format("%d", ledCube.nextMachineMove.x) +
                    //        "  y:" + String.format("%d", ledCube.nextMachineMove.y) +
                    //        " z:" + String.format("%d", ledCube.nextMachineMove.z));
                    ledCube.setTurnedOnByMachine();
                    ledCube.BlueToothWrite();
                }
                else {
                    ledCube.checkForMachineWin();
                    if (ledCube.foundNextMove) {
                        tvLeft.setText("foundNextMove");
                        ledCube.setTurnedOnByMachine();
                        ledCube.BlueToothWrite();
                    }
                    else {
                        if (ledCube.findRandomMachineMove()) {
                            tvLeft.setText("random move OK");
                            ledCube.setTurnedOnByMachine();
                            ledCube.BlueToothWrite();
                        }
                    }
                }
                MachineWins = ledCube.checkForMachineWin();
                if (MachineWins) {
                    tvLeft.setText("MachineWins");
                    ledCube.AllOff();
                    ledCube.showMachineWiningLine();
                    ledCube.BlueToothWrite();
                    toneG.startTone(ToneGenerator.TONE_SUP_ERROR, 2000);
                    break;
                }
                break;
        }

    }
}
