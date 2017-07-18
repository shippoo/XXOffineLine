package Xposed;

import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;

public class XPhoneStateListener extends PhoneStateListener {
	@Override
	public void onCellLocationChanged(CellLocation location) {
		// TODO Auto-generated method stub
		super.onCellLocationChanged(location);
	}
	@Override
	@Deprecated
	public void onSignalStrengthChanged(int asu) {
		// TODO Auto-generated method stub
		super.onSignalStrengthChanged(asu);
	}
	@Override
	public void onSignalStrengthsChanged(SignalStrength signalStrength) {
		// TODO Auto-generated method stub
		super.onSignalStrengthsChanged(signalStrength);
	}

}
