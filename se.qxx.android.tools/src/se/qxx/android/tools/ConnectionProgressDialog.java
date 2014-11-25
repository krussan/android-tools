package se.qxx.android.tools;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

public class ConnectionProgressDialog implements ResponseListener {

		private Activity activity;
		private ProgressDialog dialog = null;
		
		private Activity getActivity() {
			return activity;
		}

		private void setActivity(Activity activity) {
			this.activity = activity;
		}

		private ProgressDialog getDialog() {
			return dialog;
		}

		private void setDialog(ProgressDialog dialog) {
			this.dialog = dialog;
		}

		private ConnectionProgressDialog(Activity a, ProgressDialog d) {
			this.setDialog(d);
			this.setActivity(a);
		}

		public static ConnectionProgressDialog build(Activity a, String message) {
			ProgressDialog d = new ProgressDialog(a);		
			ConnectionProgressDialog jc = new ConnectionProgressDialog(a, d);
			d.setMessage(message);
			d.show();
			
			return jc;
		}
		
		final Handler mHandler = new Handler();	

		@Override
		public void onRequestComplete(ResponseMessage message) {
			this.getDialog().dismiss();
			Boolean success = message.result();
			final String msg = message.getMessage();
			final Context c = this.activity.getApplicationContext();
					
			if (!success) {
				this.activity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(c, msg, Toast.LENGTH_LONG).show();		
					}			
				});
			}		
		}
	}