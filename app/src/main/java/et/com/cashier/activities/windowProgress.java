package et.com.cashier.activities;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import et.com.cashier.R;

public class windowProgress
{
    public static windowProgress customProgress = null;
    private Dialog mDialog;

    public static windowProgress getInstance()
    {
        if (customProgress == null)
        {
            customProgress = new windowProgress();
        }
        return customProgress;
    }

    public void showProgress(Context context, String message, boolean cancelable)
    {
        mDialog = new Dialog(context);

        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.custom_progress_dialog);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        ProgressBar mProgressBar = (ProgressBar) mDialog.findViewById(R.id.cp_pbar);
        TextView progressText = (TextView) mDialog.findViewById(R.id.cp_title);
        progressText.setText("" + message);
        progressText.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar.setIndeterminate(true);

        mDialog.setCancelable(cancelable);
        mDialog.setCanceledOnTouchOutside(cancelable);
        mDialog.show();
    }
    public void hideProgress() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }
}
