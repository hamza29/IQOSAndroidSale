package com.example.iqos.LeadsModule;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iqos.Constants;
import com.example.iqos.R;
import com.example.iqos.Retrofit.ApiClient;
import com.example.iqos.Retrofit.ApiService;
import com.example.iqos.Retrofit.Model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HyperCareLeadsAdapter extends RecyclerView.Adapter<HyperCareLeadsAdapter.ViewHolder> {
    String tvAnswer2is = "0%";
    String tvAnswer1is = "";
    String tv14Answer2is = "0%";
    String tv14Answer1is = "";
    List<ActivityHyperCareLeads.Day1> items;
    SharedPreferences mSharedPreferences;
    String lead_id = "";
    String lead_status = "";
    String lead_name = "";
    String day;
    ProgressBar progressBar;
    private Activity context;

    public HyperCareLeadsAdapter(Activity context, List<ActivityHyperCareLeads.Day1> leads, String day, ProgressBar progressBar) {
        this.context = context;
        this.items = leads;
        this.day = day;
        this.progressBar = progressBar;
        mSharedPreferences = context.getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);

    }

    @Override
    public HyperCareLeadsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_device_leads, parent, false);
        return new HyperCareLeadsAdapter.ViewHolder(view);
    }


    public void onBindViewHolder(HyperCareLeadsAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        ActivityHyperCareLeads.Day1 item = items.get(position);
        if (item.getFirstName() != null) {
            holder.name.setText("" + item.getFirstName().toString() + " ");
        }

        if (item.getLeadStatus() != null) {
            holder.tvStatus.setText("" + item.getLeadStatus().toString());
        }
        holder.tvId.setText("" + item.getId() + " Date: " + item.getAssignedAt());

        holder.tvLastAction.setText(item.getLastAction().getType());
        holder.tvNextAction.setText(item.getNextAction().getType());
        holder.tvLastActionOutcome.setText("" + item.getLastAction().getTime());
        holder.tvNextActionOutcome.setText("" + item.getNextAction().getTime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lead_id = item.getId();
                lead_name = item.getFirstName();

                if (day.equalsIgnoreCase("Day1")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Confirmation"); // Set the dialog title
                    builder.setMessage("Are you sure you want to continue starting D1?"); // Set the dialog message

                    // Set up the buttons
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            String message = "Hi " + items.get(position).getFirstName() + ",\n\n Welcome to th Device Care Program. I'm your sales rep " + mSharedPreferences.getString(Constants.USER_NAME, "") + ". You can reach out to me any time during the next 2 weeks if you have any questions about your device.\n\n You can also contact our helpline 0800-04767 or email us at info@iqos.com.pk.\n\n This message is for registered adult users only who have independently registered for the Device Care Program. Please do not circulate this message further.";


                            showD1Dialog(message, items.get(position).getNumber());
                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    // Create and show the AlertDialog
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else if (day.equalsIgnoreCase("Day3")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Confirmation"); // Set the dialog title
                    builder.setMessage("Are you sure you want to continue starting D3?"); // Set the dialog message

                    // Set up the buttons
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            String message = "Hi " + items.get(position).getFirstName() + ", this is your sales rep " + mSharedPreferences.getString(Constants.USER_NAME, "") + ", I hope you are having a great day.\n\n Its been a few days since you purchased your ILUMA Prime. I'm calling since you registered for the device care program to address any queries you may have about your device.\n\n Resolve issues if any using GTPs\n\n Is there anything else I can help you with today?\n\n I understand this device is new to you and I want to remind you that you can call or\n message me anytime if you have a question or require customer care support.\n\n Alternatively, you can contact our helpline 0800-04767 or email us at info@iqos.com.pk for any product queries or complaints.";


                            showD3Dialog(message, items.get(position).getNumber());
                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    // Create and show the AlertDialog
                    AlertDialog dialog = builder.create();
                    dialog.show();

                } else if (day.equalsIgnoreCase("Day7")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Confirmation"); // Set the dialog title
                    builder.setMessage("Are you sure you want to continue starting D7?"); // Set the dialog message

                    // Set up the buttons
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            String message = "Hi " + items.get(position).getFirstName() + ", this is your sales rep " + mSharedPreferences.getString(Constants.USER_NAME, "") + ", how are you?\n\n Its been a week since you purchased your IQOS ILUMA Prime device, have faced any challenges with your IQOS device?\n\n";


                            showD7Dialog(message, items.get(position).getNumber());
                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    // Create and show the AlertDialog
                    AlertDialog dialog = builder.create();
                    dialog.show();

                } else if (day.equalsIgnoreCase("Day10")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Confirmation"); // Set the dialog title
                    builder.setMessage("Are you sure you want to continue starting D10?"); // Set the dialog message

                    // Set up the buttons
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            String message = "Hi " + items.get(position).getFirstName() + ",\n\n You are receiving this SMS as you requested to be informed of availability and price of consumables.\n\n " +
                                    "Genuine Terea is available at shop.terea.com.pk at pack price PKR 500.\n\n This message is intended for registered adult users only for addressing theie query. Please do not forward,disseminate or circulate this message further.";


                            showD10Dialog(message, items.get(position).getNumber());
                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    // Create and show the AlertDialog
                    AlertDialog dialog = builder.create();
                    dialog.show();

                } else if (day.equalsIgnoreCase("Day14")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Confirmation"); // Set the dialog title
                    builder.setMessage("Are you sure you want to continue starting D14?"); // Set the dialog message

                    // Set up the buttons
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            String message = "Hi " + items.get(position).getFirstName() + ", this is your sales rep " + mSharedPreferences.getString(Constants.USER_NAME, "") + ", how are you?\n\n Its been two weeks since you purchased your IQOS ILUMA Prime device, have faced any challenges with your IQOS device?\n\n";


                            showD14Dialog(message, items.get(position).getNumber());
                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    // Create and show the AlertDialog
                    AlertDialog dialog = builder.create();
                    dialog.show();

                }


            }
        });
        holder.tvAddMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showOpenMessage(item.getId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void clearData() {
    }

    public void showD1Dialog(String message, String number) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.day_one_dialog);
        dialog.getWindow().setGravity(Gravity.TOP);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        Button btnRefresh = dialog.findViewById(R.id.btnDone);
//        TextView tvTitle =   dialog.findViewById(R.id.tvTitle);
        EditText tvMessage = dialog.findViewById(R.id.tvMessage);

//        tvTitle.setText(""+ message);
        tvMessage.setText("" + message);

        ImageView ivBack = dialog.findViewById(R.id.ivBack);


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();


            }
        });


        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("QueryPermissionsNeeded")
            @Override
            public void onClick(View view) {


                String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());


                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.putExtra("address", "" + number);
                smsIntent.putExtra("sms_body", "" + tvMessage.getText().toString());
                context.startActivity(smsIntent);

                lead_status = "Day 1 message done";


                updateLeadD1(mSharedPreferences.getString(Constants.BAREAR_TOKEN, ""), lead_id,
                        lead_status, currentDate);


            }
        });

        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        dialog.show();
    }

    public void showD10Dialog(String message, String number) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.day_ten_dialog);
        dialog.getWindow().setGravity(Gravity.TOP);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        Button btnRefresh = dialog.findViewById(R.id.btnDone);
//        TextView tvTitle =   dialog.findViewById(R.id.tvTitle);
        EditText tvMessage = dialog.findViewById(R.id.tvMessage);

//        tvTitle.setText(""+ message);
        tvMessage.setText("" + message);

        ImageView ivBack = dialog.findViewById(R.id.ivBack);


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();


            }
        });


        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("QueryPermissionsNeeded")
            @Override
            public void onClick(View view) {


                String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());


                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.putExtra("address", "" + number);
                smsIntent.putExtra("sms_body", "" + tvMessage.getText().toString());
                context.startActivity(smsIntent);


                lead_status = "Day 10 message done";

                updateLeadD10(mSharedPreferences.getString(Constants.BAREAR_TOKEN, ""), lead_id,
                        lead_status, currentDate);


            }
        });

        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        dialog.show();
    }

    public void showD3Dialog(String message, String number) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.day_three_dialog);
        dialog.getWindow().setGravity(Gravity.TOP);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        Button btnCall = dialog.findViewById(R.id.btnCall);
        Button btnMessage = dialog.findViewById(R.id.btnMessage);
//        TextView tvTitle =   dialog.findViewById(R.id.tvTitle);
        EditText tvMessage = dialog.findViewById(R.id.tvMessage);

//        tvTitle.setText(""+ message);
        tvMessage.setText("" + message);

        ImageView ivBack = dialog.findViewById(R.id.ivBack);


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();


            }
        });


        btnMessage.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("QueryPermissionsNeeded")
            @Override
            public void onClick(View view) {


                String message = "Hi " + lead_name + ",  context is your sales rep " + mSharedPreferences.getString(Constants.USER_NAME, "") + ", \n\n I was calling to ask if you require any assistance with your ILUMA Prime.I\n understand you may be busy & were unable to attend the call.\n\n You can reach me through call or message at any time.\n\n Or you can contact our helpline 0800-04767 or email us at info@iqos.com.pk for\n any product queries or complaints.\n\n This message is for registered adult users only who have independently registered for\n the Device Care Program. Please do not circulate  context message further.";

                showD3MessageDialog(message, number);


//                String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
//
//
//                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
//                smsIntent.setType("vnd.android-dir/mms-sms");
//                smsIntent.putExtra("address", ""+number);
//                smsIntent.putExtra("sms_body",""+ tvMessage.getText().toString());
//                  context.startActivity(smsIntent);
//
//
//
//
//                updateLeadD3Message(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),lead_id,
//                        lead_status,currentDate);


            }
        });
        btnCall.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("QueryPermissionsNeeded")
            @Override
            public void onClick(View view) {


                String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());


                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + number));
                context.startActivity(intent);
                lead_status = "Day 3 call done";

                updateLeadD3Call(mSharedPreferences.getString(Constants.BAREAR_TOKEN, ""), lead_id,
                        lead_status, currentDate);


            }
        });
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);


        dialog.show();
    }

    public void showD3MessageDialog(String message, String number) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.day_three_message_dialog);
        dialog.getWindow().setGravity(Gravity.TOP);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        Button btnMessage = dialog.findViewById(R.id.btnMessage);
//        TextView tvTitle =   dialog.findViewById(R.id.tvTitle);
        EditText tvMessage = dialog.findViewById(R.id.tvMessage);

//        tvTitle.setText(""+ message);
        tvMessage.setText("" + message);

        ImageView ivBack = dialog.findViewById(R.id.ivBack);


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();


            }
        });


        btnMessage.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("QueryPermissionsNeeded")
            @Override
            public void onClick(View view) {


                String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());


                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.putExtra("address", "" + number);
                smsIntent.putExtra("sms_body", "" + tvMessage.getText().toString());
                context.startActivity(smsIntent);


                lead_status = "Day 3 message done";


                updateLeadD3Message(mSharedPreferences.getString(Constants.BAREAR_TOKEN, ""), lead_id,
                        lead_status, currentDate);


            }
        });


        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);


        dialog.show();
    }

    public void showD7MessageDialog(String message, String number) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.day_seven_message_dialog);
        dialog.getWindow().setGravity(Gravity.TOP);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        Button btnMessage = dialog.findViewById(R.id.btnMessage);
//        TextView tvTitle =   dialog.findViewById(R.id.tvTitle);
        EditText tvMessage = dialog.findViewById(R.id.tvMessage);

//        tvTitle.setText(""+ message);
        tvMessage.setText("" + message);

        ImageView ivBack = dialog.findViewById(R.id.ivBack);


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();


            }
        });


        btnMessage.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("QueryPermissionsNeeded")
            @Override
            public void onClick(View view) {


                String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());


                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.putExtra("address", "" + number);
                smsIntent.putExtra("sms_body", "" + tvMessage.getText().toString());
                context.startActivity(smsIntent);


                lead_status = "Day 7 message done";


                updateLeadD7Message(mSharedPreferences.getString(Constants.BAREAR_TOKEN, ""), lead_id,
                        lead_status, currentDate);


            }
        });


        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);


        dialog.show();
    }

    public void showD7Dialog(String message, String number) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.day_seven_dialog);
        dialog.getWindow().setGravity(Gravity.TOP);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        Button btnCall = dialog.findViewById(R.id.btnCall);
        Button btnMessage = dialog.findViewById(R.id.btnMessage);
//        TextView tvTitle =   dialog.findViewById(R.id.tvTitle);
        EditText tvMessage = dialog.findViewById(R.id.tvMessage);
        TextView tvBookAppointment = dialog.findViewById(R.id.tvBookAppointment);
        Button btnSubmit = dialog.findViewById(R.id.btnSubmit);
        EditText tvAnswer1 = dialog.findViewById(R.id.etQ1a);
        CheckBox cbIQOS = dialog.findViewById(R.id.cbIQOS);
        CheckBox cbCigrattes = dialog.findViewById(R.id.cbCigrattes);
        CheckBox cbPouch = dialog.findViewById(R.id.cbPouch);
        CheckBox cbOther = dialog.findViewById(R.id.cbOther);
        CheckBox cbVpae = dialog.findViewById(R.id.cbVpae);
        CheckBox cbNicotine = dialog.findViewById(R.id.cbNicotine);
        RadioGroup rbIsNotify = dialog.findViewById(R.id.rbIsNotify);


        TextView tvQuestion1 = dialog.findViewById(R.id.tvQuestion1);
        EditText tvAnswer2 = dialog.findViewById(R.id.etQ2a);


        TextView tvQuestion2 = dialog.findViewById(R.id.tvQuestion2);
        EditText tvAnswer3 = dialog.findViewById(R.id.etQ3a);
        TextView tvQuestion3 = dialog.findViewById(R.id.tvQuestion3);

        tvBookAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, AppointmentBookingDetailHyperActivity.class);
                intent.putExtra("lead_id", "" + lead_id);
                intent.putExtra("name", "" + lead_name.trim());
                intent.putExtra("type", "hc1");
                context.startActivity(intent);


            }
        });


        rbIsNotify.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) dialog.findViewById(selectedId);

                if (radioButton.getText().toString().equalsIgnoreCase("0%")) {
                    tvAnswer2is = "0%";
                } else if (radioButton.getText().toString().equalsIgnoreCase("0.1% - 69%")) {
                    tvAnswer2is = "0.1% - 69%";

                } else if (radioButton.getText().toString().equalsIgnoreCase("70% - 94%")) {
                    tvAnswer2is = "70% - 94%";

                } else if (radioButton.getText().toString().equalsIgnoreCase("95% - 100%")) {
                    tvAnswer2is = "95% - 100%";

                }
            }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (cbCigrattes.isChecked()) {
                    tvAnswer1is = cbCigrattes.getText().toString() + ", ";
                }
                if (cbIQOS.isChecked()) {
                    tvAnswer1is = tvAnswer1is + cbIQOS.getText().toString() + ", ";

                }
                if (cbNicotine.isChecked()) {
                    tvAnswer1is = tvAnswer1is + cbNicotine.getText().toString() + ", ";

                }
                if (cbOther.isChecked()) {
                    tvAnswer1is = tvAnswer1is + cbOther.getText().toString() + ", ";

                }
                if (cbPouch.isChecked()) {
                    tvAnswer1is = tvAnswer1is + cbPouch.getText().toString() + ", ";

                }
                if (cbVpae.isChecked()) {
                    tvAnswer1is = tvAnswer1is + cbVpae.getText().toString() + ", ";

                }
                ;
                lead_status = "Day 7 Feedback submit";
                updateLeadD7Feedback(mSharedPreferences.getString(Constants.BAREAR_TOKEN, ""), lead_id,
                        lead_status, tvQuestion1.getText().toString(), tvAnswer1is.substring(0, tvAnswer1is.length() - 2),
                        tvQuestion2.getText().toString(), tvAnswer2is, tvQuestion3.getText().toString(), tvAnswer3.getText().toString());
            }
        });
//        tvTitle.setText(""+ message);
        tvMessage.setText("" + message);

        ImageView ivBack = dialog.findViewById(R.id.ivBack);


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();


            }
        });


        btnMessage.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("QueryPermissionsNeeded")
            @Override
            public void onClick(View view) {


                String message = "Hi " + lead_name + ", \n\n Its been a week since you purchased your IQOS device.\n\n If you are facing any challenges, please reach out to me without hesitation.\n\n" +
                        "Alternatively you can also contact our helpline 0800-04767 or email us at info@iqos.com.pk for any product queries or complaints.\n\n This message is for registered adult users only who have independently registered for\n the Device Care Program. Please do not circulate  context message further.";

                showD7MessageDialog(message, number);

            }
        });


        btnCall.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("QueryPermissionsNeeded")
            @Override
            public void onClick(View view) {


                String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());


                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + number));
                context.startActivity(intent);
                lead_status = "Day 7 call done";
                updateLeadD7Call(mSharedPreferences.getString(Constants.BAREAR_TOKEN, ""), lead_id,
                        lead_status, currentDate);


            }
        });

        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        dialog.show();
    }

    public void showD14Dialog(String message, String number) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.day_fourteen_dialog);
        dialog.getWindow().setGravity(Gravity.TOP);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        Button btnCall = dialog.findViewById(R.id.btnCall);

        EditText tvMessage = dialog.findViewById(R.id.tvMessage);

        tvMessage.setText("" + message);

        ImageView ivBack = dialog.findViewById(R.id.ivBack);
        TextView tvBookAppointment = dialog.findViewById(R.id.tvBookAppointment);
        Button btnSubmit = dialog.findViewById(R.id.btnSubmit);
//        EditText tvAnswer1 =   dialog.findViewById(R.id.etQ1a);
        TextView tvQuestion1 = dialog.findViewById(R.id.tvQuestion1);
//        EditText tvAnswer2 =   dialog.findViewById(R.id.etQ2a);
        TextView tvQuestion2 = dialog.findViewById(R.id.tvQuestion2);
        EditText tvAnswer3 = dialog.findViewById(R.id.etQ3a);
        TextView tvQuestion3 = dialog.findViewById(R.id.tvQuestion3);

        CheckBox cbIQOS = dialog.findViewById(R.id.cbIQOS);
        CheckBox cbCigrattes = dialog.findViewById(R.id.cbCigrattes);
        CheckBox cbPouch = dialog.findViewById(R.id.cbPouch);
        CheckBox cbOther = dialog.findViewById(R.id.cbOther);
        CheckBox cbVpae = dialog.findViewById(R.id.cbVpae);
        CheckBox cbNicotine = dialog.findViewById(R.id.cbNicotine);
        RadioGroup rbIsNotify = dialog.findViewById(R.id.rbIsNotify);


        rbIsNotify.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) dialog.findViewById(selectedId);

                if (radioButton.getText().toString().equalsIgnoreCase("0%")) {
                    tv14Answer2is = "0%";
                } else if (radioButton.getText().toString().equalsIgnoreCase("0.1% - 69%")) {
                    tv14Answer2is = "0.1% - 69%";

                } else if (radioButton.getText().toString().equalsIgnoreCase("70% - 94%")) {
                    tv14Answer2is = "70% - 94%";

                } else if (radioButton.getText().toString().equalsIgnoreCase("95% - 100%")) {
                    tv14Answer2is = "95% - 100%";

                }
            }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (cbCigrattes.isChecked()) {
                    tv14Answer1is = cbCigrattes.getText().toString() + ", ";
                }
                if (cbIQOS.isChecked()) {
                    tv14Answer1is = tv14Answer1is + cbIQOS.getText().toString() + ", ";

                }
                if (cbNicotine.isChecked()) {
                    tv14Answer1is = tv14Answer1is + cbNicotine.getText().toString() + ", ";

                }
                if (cbOther.isChecked()) {
                    tv14Answer1is = tv14Answer1is + cbOther.getText().toString() + ", ";

                }

                if (cbPouch.isChecked()) {
                    tv14Answer1is = tv14Answer1is + cbPouch.getText().toString() + ", ";

                }

                if (cbVpae.isChecked()) {
                    tv14Answer1is = tv14Answer1is + cbVpae.getText().toString() + ", ";

                }


                lead_status = "Day 14 Feedback submit";
                updateLeadD14Feedback(mSharedPreferences.getString(Constants.BAREAR_TOKEN, ""), lead_id,
                        lead_status, tvQuestion1.getText().toString(), tv14Answer1is.substring(0, tv14Answer1is.length() - 2),
                        tvQuestion2.getText().toString(), tv14Answer2is, tvQuestion3.getText().toString(), tvAnswer3.getText().toString());
            }
        });
        tvBookAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, AppointmentBookingDetailHyperActivity.class);
                intent.putExtra("lead_id", "" + lead_id);
                intent.putExtra("name", "" + lead_name.trim());
                intent.putExtra("type", "hc2");

                context.startActivity(intent);

            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();


            }
        });


        btnCall.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("QueryPermissionsNeeded")
            @Override
            public void onClick(View view) {


                String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());


                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + number));
                context.startActivity(intent);
                lead_status = "Day 14 call done";

                updateLeadD14Call(mSharedPreferences.getString(Constants.BAREAR_TOKEN, ""), lead_id,
                        lead_status, currentDate);


            }
        });
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        dialog.show();
    }

    public void updateLeadD1(String token, String id, String lead_status,
                             String day1_message_at) {
        progressBar.setVisibility(View.VISIBLE);
        ApiService apiService = ApiClient.getClient(context).create(ApiService.class);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        builder.addFormDataPart("id", id);
        builder.addFormDataPart("lead_status", lead_status);

        builder.addFormDataPart("day1_message_at", day1_message_at);


        RequestBody requestBody = builder.build();

        Call<Model.GenerealModel> call = apiService.updateHyperLead(token, requestBody);

        call.enqueue(new Callback<Model.GenerealModel>() {
            @Override
            public void onResponse(Call<Model.GenerealModel> call, Response<Model.GenerealModel> response) {
                final Model.GenerealModel listofhome = response.body();
                if (listofhome != null) {

                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            context.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                            progressBar.setVisibility(View.GONE);

                            if (listofhome.getStatus().equals("1")) {

//                                getLeadsDetails(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),lead_id);

                                Toast.makeText(context, "" + listofhome.getMessage(), Toast.LENGTH_SHORT).show();
//                                getLeadsDetails(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),lead_id);


                            } else {
                                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);

                            }
                        }
                    });
                } else {

                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            context.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }


            }

            @Override
            public void onFailure(Call<Model.GenerealModel> call, Throwable t) {


                context.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                    }

                });


            }
        });
    }

    public void updateLeadD10(String token, String id, String lead_status,
                              String day1_message_at) {
        progressBar.setVisibility(View.VISIBLE);
        ApiService apiService = ApiClient.getClient(context).create(ApiService.class);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        builder.addFormDataPart("id", id);
        builder.addFormDataPart("lead_status", lead_status);

        builder.addFormDataPart("day10_message_at", day1_message_at);


        RequestBody requestBody = builder.build();

        Call<Model.GenerealModel> call = apiService.updateHyperLead(token, requestBody);

        call.enqueue(new Callback<Model.GenerealModel>() {
            @Override
            public void onResponse(Call<Model.GenerealModel> call, Response<Model.GenerealModel> response) {
                final Model.GenerealModel listofhome = response.body();
                if (listofhome != null) {

                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            context.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                            progressBar.setVisibility(View.GONE);

                            if (listofhome.getStatus().equals("1")) {
//                                if(dialog!=null) {
//                                    dialog.dismiss();
//                                }
//                                getLeadsDetails(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),lead_id);

                                Toast.makeText(context, "" + listofhome.getMessage(), Toast.LENGTH_SHORT).show();
//                                getLeadsDetails(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),lead_id);


                            } else {
                                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);

                            }
                        }
                    });
                } else {

                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            context.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }


            }

            @Override
            public void onFailure(Call<Model.GenerealModel> call, Throwable t) {


                context.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                    }

                });


            }
        });
    }

    public void updateLeadD3Message(String token, String id, String lead_status,
                                    String day1_message_at) {
        progressBar.setVisibility(View.VISIBLE);
        ApiService apiService = ApiClient.getClient(context).create(ApiService.class);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        builder.addFormDataPart("id", id);
        builder.addFormDataPart("lead_status", lead_status);

        builder.addFormDataPart("day3_message_at", day1_message_at);


        RequestBody requestBody = builder.build();

        Call<Model.GenerealModel> call = apiService.updateHyperLead(token, requestBody);

        call.enqueue(new Callback<Model.GenerealModel>() {
            @Override
            public void onResponse(Call<Model.GenerealModel> call, Response<Model.GenerealModel> response) {
                final Model.GenerealModel listofhome = response.body();
                if (listofhome != null) {

                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            context.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                            progressBar.setVisibility(View.GONE);

                            if (listofhome.getStatus().equals("1")) {
//                                if(dialog!=null) {
//                                    dialog.dismiss();
//                                }
//                                getLeadsDetails(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),lead_id);

                                Toast.makeText(context, "" + listofhome.getMessage(), Toast.LENGTH_SHORT).show();
//                                getLeadsDetails(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),lead_id);


                            } else {
                                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);

                            }
                        }
                    });
                } else {

                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            context.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }


            }

            @Override
            public void onFailure(Call<Model.GenerealModel> call, Throwable t) {


                context.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                    }

                });


            }
        });
    }

    public void updateLeadD3Call(String token, String id, String lead_status,
                                 String day1_message_at) {
        progressBar.setVisibility(View.VISIBLE);
        ApiService apiService = ApiClient.getClient(context).create(ApiService.class);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        builder.addFormDataPart("id", id);
        builder.addFormDataPart("lead_status", lead_status);

        builder.addFormDataPart("day3_call_at", day1_message_at);


        RequestBody requestBody = builder.build();

        Call<Model.GenerealModel> call = apiService.updateHyperLead(token, requestBody);

        call.enqueue(new Callback<Model.GenerealModel>() {
            @Override
            public void onResponse(Call<Model.GenerealModel> call, Response<Model.GenerealModel> response) {
                final Model.GenerealModel listofhome = response.body();
                if (listofhome != null) {

                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            context.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                            progressBar.setVisibility(View.GONE);

                            if (listofhome.getStatus().equals("1")) {

//                                getLeadsDetails(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),lead_id);

                                Toast.makeText(context, "" + listofhome.getMessage(), Toast.LENGTH_SHORT).show();
//                                getLeadsDetails(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),lead_id);


                            } else {
                                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);

                            }
                        }
                    });
                } else {

                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            context.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }


            }

            @Override
            public void onFailure(Call<Model.GenerealModel> call, Throwable t) {


                context.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                    }

                });


            }
        });
    }

    public void updateLeadD7Message(String token, String id, String lead_status,
                                    String day1_message_at) {
        progressBar.setVisibility(View.VISIBLE);
        ApiService apiService = ApiClient.getClient(context).create(ApiService.class);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        builder.addFormDataPart("id", id);
        builder.addFormDataPart("lead_status", lead_status);

        builder.addFormDataPart("day7_message_at", day1_message_at);


        RequestBody requestBody = builder.build();

        Call<Model.GenerealModel> call = apiService.updateHyperLead(token, requestBody);

        call.enqueue(new Callback<Model.GenerealModel>() {
            @Override
            public void onResponse(Call<Model.GenerealModel> call, Response<Model.GenerealModel> response) {
                final Model.GenerealModel listofhome = response.body();
                if (listofhome != null) {

                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            context.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                            progressBar.setVisibility(View.GONE);

                            if (listofhome.getStatus().equals("1")) {

//                                getLeadsDetails(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),lead_id);

                                Toast.makeText(context, "" + listofhome.getMessage(), Toast.LENGTH_SHORT).show();
//                                getLeadsDetails(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),lead_id);


                            } else {
                                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);

                            }
                        }
                    });
                } else {

                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            context.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }


            }

            @Override
            public void onFailure(Call<Model.GenerealModel> call, Throwable t) {


                context.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                    }

                });


            }
        });
    }

    public void updateLeadD7Call(String token, String id, String lead_status,
                                 String day1_message_at) {
        progressBar.setVisibility(View.VISIBLE);
        ApiService apiService = ApiClient.getClient(context).create(ApiService.class);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        builder.addFormDataPart("id", id);
        builder.addFormDataPart("lead_status", lead_status);

        builder.addFormDataPart("day7_call_at", day1_message_at);


        RequestBody requestBody = builder.build();

        Call<Model.GenerealModel> call = apiService.updateHyperLead(token, requestBody);

        call.enqueue(new Callback<Model.GenerealModel>() {
            @Override
            public void onResponse(Call<Model.GenerealModel> call, Response<Model.GenerealModel> response) {
                final Model.GenerealModel listofhome = response.body();
                if (listofhome != null) {

                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            context.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                            progressBar.setVisibility(View.GONE);

                            if (listofhome.getStatus().equals("1")) {

//                                getLeadsDetails(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),lead_id);

                                Toast.makeText(context, "" + listofhome.getMessage(), Toast.LENGTH_SHORT).show();
//                                getLeadsDetails(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),lead_id);


                            } else {
                                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);

                            }
                        }
                    });
                } else {

                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            context.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }


            }

            @Override
            public void onFailure(Call<Model.GenerealModel> call, Throwable t) {


                context.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                    }

                });


            }
        });
    }

    public void updateLeadD7Feedback(String token, String id, String lead_status,
                                     String day7_question1,
                                     String day7_answer1,
                                     String day7_question2,
                                     String day7_answer2,
                                     String day7_question3,
                                     String day7_answer3) {
        progressBar.setVisibility(View.VISIBLE);
        ApiService apiService = ApiClient.getClient(context).create(ApiService.class);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        builder.addFormDataPart("id", id);
        builder.addFormDataPart("lead_status", lead_status);

        builder.addFormDataPart("day7_question1", day7_question1);
        builder.addFormDataPart("day7_answer1", day7_answer1);
        builder.addFormDataPart("day7_question2", day7_question2);
        builder.addFormDataPart("day7_answer2", day7_answer2);
        builder.addFormDataPart("day7_question3", day7_question3);
        builder.addFormDataPart("day7_answer3", day7_answer3);


        RequestBody requestBody = builder.build();

        Call<Model.GenerealModel> call = apiService.updateHyperLead(token, requestBody);

        call.enqueue(new Callback<Model.GenerealModel>() {
            @Override
            public void onResponse(Call<Model.GenerealModel> call, Response<Model.GenerealModel> response) {
                final Model.GenerealModel listofhome = response.body();
                if (listofhome != null) {

                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            context.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                            progressBar.setVisibility(View.GONE);

                            if (listofhome.getStatus().equals("1")) {

//                                getLeadsDetails(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),lead_id);

                                Toast.makeText(context, "" + listofhome.getMessage(), Toast.LENGTH_SHORT).show();
//                                getLeadsDetails(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),lead_id);


                            } else {
                                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);

                            }
                        }
                    });
                } else {

                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            context.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }


            }

            @Override
            public void onFailure(Call<Model.GenerealModel> call, Throwable t) {


                context.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                    }

                });


            }
        });
    }

    public void updateLeadD14Feedback(String token, String id, String lead_status,
                                      String day7_question1,
                                      String day7_answer1,
                                      String day7_question2,
                                      String day7_answer2,
                                      String day7_question3,
                                      String day7_answer3) {
        progressBar.setVisibility(View.VISIBLE);
        ApiService apiService = ApiClient.getClient(context).create(ApiService.class);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        builder.addFormDataPart("id", id);
        builder.addFormDataPart("lead_status", lead_status);

        builder.addFormDataPart("day14_question1", day7_question1);
        builder.addFormDataPart("day14_answer1", day7_answer1);
        builder.addFormDataPart("day14_question2", day7_question2);
        builder.addFormDataPart("day14_answer2", day7_answer2);
        builder.addFormDataPart("day14_question3", day7_question3);
        builder.addFormDataPart("day14_answer3", day7_answer3);


        RequestBody requestBody = builder.build();

        Call<Model.GenerealModel> call = apiService.updateHyperLead(token, requestBody);

        call.enqueue(new Callback<Model.GenerealModel>() {
            @Override
            public void onResponse(Call<Model.GenerealModel> call, Response<Model.GenerealModel> response) {
                final Model.GenerealModel listofhome = response.body();
                if (listofhome != null) {

                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            context.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                            progressBar.setVisibility(View.GONE);

                            if (listofhome.getStatus().equals("1")) {

//                                getLeadsDetails(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),lead_id);

                                Toast.makeText(context, "" + listofhome.getMessage(), Toast.LENGTH_SHORT).show();
//                                getLeadsDetails(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),lead_id);


                            } else {
                                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);

                            }
                        }
                    });
                } else {

                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            context.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }


            }

            @Override
            public void onFailure(Call<Model.GenerealModel> call, Throwable t) {


                context.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                    }

                });


            }
        });
    }

    public void updateLeadD14Call(String token, String id, String lead_status,
                                  String day1_message_at) {
        progressBar.setVisibility(View.VISIBLE);
        ApiService apiService = ApiClient.getClient(context).create(ApiService.class);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        builder.addFormDataPart("id", id);
        builder.addFormDataPart("lead_status", lead_status);

        builder.addFormDataPart("day14_call_at", day1_message_at);


        RequestBody requestBody = builder.build();

        Call<Model.GenerealModel> call = apiService.updateHyperLead(token, requestBody);

        call.enqueue(new Callback<Model.GenerealModel>() {
            @Override
            public void onResponse(Call<Model.GenerealModel> call, Response<Model.GenerealModel> response) {
                final Model.GenerealModel listofhome = response.body();
                if (listofhome != null) {

                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            context.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                            progressBar.setVisibility(View.GONE);

                            if (listofhome.getStatus().equals("1")) {

//                                getLeadsDetails(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),lead_id);

                                Toast.makeText(context, "" + listofhome.getMessage(), Toast.LENGTH_SHORT).show();
//                                getLeadsDetails(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),lead_id);


                            } else {
                                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);

                            }
                        }
                    });
                } else {

                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            context.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }


            }

            @Override
            public void onFailure(Call<Model.GenerealModel> call, Throwable t) {


                context.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                    }

                });


            }
        });
    }

    public void showOpenMessage(String lead_id) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.day_custom_dialog);
        dialog.getWindow().setGravity(Gravity.TOP);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        Button btnRefresh = dialog.findViewById(R.id.btnDone);
//        TextView tvTitle =   dialog.findViewById(R.id.tvTitle);
        EditText tvMessage = dialog.findViewById(R.id.tvMessage);

        ImageView ivBack = dialog.findViewById(R.id.ivBack);


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();


            }
        });


        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("QueryPermissionsNeeded")
            @Override
            public void onClick(View view) {


                updateLeadMEssaage(mSharedPreferences.
                                getString(Constants.BAREAR_TOKEN, ""), lead_id,
                        tvMessage.getText().toString());


            }
        });

        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        dialog.show();
    }

    public void updateLeadMEssaage(String token, String id, String message) {

        ApiService apiService = ApiClient.getClient(context).create(ApiService.class);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        builder.addFormDataPart("lead_id", id);
        builder.addFormDataPart("message", message);


        RequestBody requestBody = builder.build();

        Call<Model.GenerealModel> call = apiService.custom_message(token, requestBody);

        call.enqueue(new Callback<Model.GenerealModel>() {
            @Override
            public void onResponse(Call<Model.GenerealModel> call, Response<Model.GenerealModel> response) {
                final Model.GenerealModel listofhome = response.body();
                if (listofhome != null) {

                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            context.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);


                            if (listofhome.getStatus().equals("1")) {

//                                getLeadsDetails(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),lead_id);

                                Toast.makeText(context, "" + listofhome.getMessage(), Toast.LENGTH_SHORT).show();
//                                getLeadsDetails(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),lead_id);


                            } else {
                                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                } else {

                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            context.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }


            }

            @Override
            public void onFailure(Call<Model.GenerealModel> call, Throwable t) {


                context.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                    }

                });


            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        TextView name;
        TextView tvStatus;
        TextView tvLastAction;
        TextView tvNextAction;
        TextView tvId;
        TextView tvLastActionOutcome;
        TextView tvNextActionOutcome;
        TextView tvAddMessage;


        public ViewHolder(View itemView) {
            super(itemView);

            tvAddMessage = itemView.findViewById(R.id.tvAddMessage);
            name = itemView.findViewById(R.id.tvName);
            tvLastAction = itemView.findViewById(R.id.tvLastAction);
            name = itemView.findViewById(R.id.tvName);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvNextAction = itemView.findViewById(R.id.tvNextAction);
            tvId = itemView.findViewById(R.id.tvId);
            tvLastActionOutcome = itemView.findViewById(R.id.tvLastActionOutcome);
            tvNextActionOutcome = itemView.findViewById(R.id.tvNextActionOutcome);


        }
    }


}