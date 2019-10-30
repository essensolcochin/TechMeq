package com.essensol.techmeq.POS_Printer_Util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.printer.service.PrinterPrintService;

public class DeviceBootReceiver extends BroadcastReceiver {
	static final String BOOT_COMPLETED = "android.intent.action.BOOT_COMPLETED";

	/** {@inheritDoc} */
	@RequiresApi(api = Build.VERSION_CODES.O)
	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(BOOT_COMPLETED)) {
			Intent i = new Intent(context, PrinterPrintService.class);
			context.startForegroundService(i);
			Log.e("DeviceBootReceiver", "GpPrintService.start");
		}
	}
}
