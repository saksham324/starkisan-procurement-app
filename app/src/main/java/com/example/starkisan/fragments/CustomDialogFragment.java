package com.example.starkisan.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.fragment.app.DialogFragment;
import com.example.starkisan.R;
import com.example.starkisan.CommodityEntryActivity;
import com.farbod.labelledspinner.LabelledSpinner;
import java.io.File;
import java.util.Arrays;
import java.util.Calendar;

public class CustomDialogFragment extends DialogFragment {
    private static final String ARG_DIALOG_ID_KEY = "dialog_id";
    public static final int COMMODITY_DIALOG_ID = 5;
    public static final int DATE_DIALOG_ID = 1;
    private static final String FILENAME_KEY = "filename_key";
    public static final int GRADE_DIALOG_ID = 6;
    public static final int MANDI_DIALOG_ID = 3;
    public static final int PHOTO_PICKER_DIALOG_ID = 0;
    public static final int PHOTO_PICKER_FROM_CAMERA_ID = 0;
    public static final int PHOTO_PICKER_FROM_GALLERY_ID = 1;
    public static final int RATE_DIALOG_ID = 7;
    public static final int REMARKS_DIALOG_ID = 4;
    private static final int REQUEST_CODE_TAKE_FROM_CAMERA = 0;
    private static final int REQUEST_CODE_TAKE_FROM_GALLERY = 1;
    public static final int SELLER_DIALOG_ID = 8;
    private String commodityName;
    private String grade;
    private File mImageFile;
    private Uri mImageUri;
    private String mandiName;

    public static CustomDialogFragment newInstance(int dialogId) {
        CustomDialogFragment fragment = new CustomDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_DIALOG_ID_KEY, dialogId);
        fragment.setArguments(args);
        return fragment;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        switch (getArguments().getInt(ARG_DIALOG_ID_KEY, -1)) {
            case PHOTO_PICKER_DIALOG_ID:
                return createImageUrlDialog().create();
            case DATE_DIALOG_ID:
                return createDateDialog();
            case MANDI_DIALOG_ID:
                return createMandiDialog().create();
            case REMARKS_DIALOG_ID:
                return createRemarksDialog().create();
            case COMMODITY_DIALOG_ID:
                return createCommodityDialog().create();
            case GRADE_DIALOG_ID:
                return createGradeDialog().create();
            case RATE_DIALOG_ID:
                return createNumberDialog().create();
            case SELLER_DIALOG_ID:
                return createSellersDialog().create();
            default:
                return super.onCreateDialog(savedInstanceState);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_custom_dialog, container, false);
    }

    private DatePickerDialog createDateDialog() {
        int day;
        int month;
        int year;
        CommodityEntryActivity context = (CommodityEntryActivity) getActivity();
        String curr = context.getData(5);
        if (curr.equals("today")) {
            Calendar c = Calendar.getInstance();
            int year2 = c.get(Calendar.YEAR);
            int month2 = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
            year = year2;
            month = month2;
        } else {
            String[] arr = curr.split("-");
            int year3 = Integer.parseInt(arr[0]);
            int month3 = Integer.parseInt(arr[1]);
            day = Integer.parseInt(arr[2].substring(0, 2));
            month = month3;
            year = year3;
        }
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, context.onDatePick, year, month, day);
        return datePickerDialog;
    }

    private Builder createRemarksDialog() {
        CommodityEntryActivity context = (CommodityEntryActivity) getActivity();
        Builder dialogBuilder = new Builder(context);
        dialogBuilder.setTitle((CharSequence) "Remarks");
        View dialogView = requireActivity().getLayoutInflater().inflate(R.layout.dialog_fragment_comment, null);
        ((EditText) dialogView.findViewById(R.id.commentEditText)).setText(context.getData(4));
        String str = "Cancel";
        dialogBuilder.setView(dialogView).setPositiveButton((CharSequence) "OK", context.commentDialogHandler).setNegativeButton((CharSequence) str, context.commentDialogHandler);
        return dialogBuilder;
    }

    private Builder createNumberDialog() {
        CommodityEntryActivity context = (CommodityEntryActivity) getActivity();
        Builder dialogBuilder = new Builder(context);
        dialogBuilder.setTitle((CharSequence) "Rate");
        View dialogView = requireActivity().getLayoutInflater().inflate(R.layout.dialog_fragment_number, null);
        ((EditText) dialogView.findViewById(R.id.numberEditText)).setText(context.getData(3));
        String str = "Cancel";
        dialogBuilder.setView(dialogView).setPositiveButton((CharSequence) "OK", context.numberDialogHandler).setNegativeButton((CharSequence) str, context.numberDialogHandler);
        return dialogBuilder;
    }

    private Builder createMandiDialog() {
        CommodityEntryActivity context = (CommodityEntryActivity) getActivity();
        Builder dialogBuilder = new Builder(context);
        dialogBuilder.setTitle((CharSequence) "Mandi");
        View dialogView = requireActivity().getLayoutInflater().inflate(R.layout.dialog_fragment_activity, null);
        int i = 0;
        int index = Arrays.asList(getResources().getStringArray(R.array.items_spinner_mandi)).indexOf(context.getData(0));
        LabelledSpinner labelledSpinner = (LabelledSpinner) dialogView.findViewById(R.id.dialogSpinner);
        if (index > -1) {
            i = index;
        }
        labelledSpinner.setSelection(i);
        String str = "Cancel";
        dialogBuilder.setView(dialogView).setPositiveButton((CharSequence) "OK", context.mandiDialogHandler).setNegativeButton((CharSequence) str, context.mandiDialogHandler);
        return dialogBuilder;
    }

    private Builder createCommodityDialog() {
        CommodityEntryActivity context = (CommodityEntryActivity) getActivity();
        Builder dialogBuilder = new Builder(context);
        dialogBuilder.setTitle((CharSequence) "Commodity");
        View dialogView = requireActivity().getLayoutInflater().inflate(R.layout.dialog_commodity_fragment, null);
        int index = Arrays.asList(getResources().getStringArray(R.array.items_spinner_commodity)).indexOf(context.getData(1));
        ((LabelledSpinner) dialogView.findViewById(R.id.dialogCommoditySpinner)).setSelection(index > -1 ? index : 0);
        dialogBuilder.setView(dialogView).setPositiveButton((CharSequence) "OK", context.commodityDialogHandler).setNegativeButton((CharSequence) "Cancel", context.commodityDialogHandler);
        return dialogBuilder;
    }

    private Builder createGradeDialog() {
        CommodityEntryActivity context = (CommodityEntryActivity) getActivity();
        Builder dialogBuilder = new Builder(context);
        dialogBuilder.setTitle((CharSequence) "Grade");
        View dialogView = requireActivity().getLayoutInflater().inflate(R.layout.dialog_grade_fragment, null);
        int index = Arrays.asList(getResources().getStringArray(R.array.items_spinner_grade)).indexOf(context.getData(2));
        ((LabelledSpinner) dialogView.findViewById(R.id.dialogGradeSpinner)).setSelection(index > -1 ? index : 0);
        dialogBuilder.setView(dialogView).setPositiveButton((CharSequence) "OK", context.gradeDialogHandler).setNegativeButton((CharSequence) "Cancel", context.gradeDialogHandler);
        return dialogBuilder;
    }

    private Builder createImageUrlDialog() {
        CommodityEntryActivity context = (CommodityEntryActivity) getActivity();
        Builder dialogBuilder = new Builder(context);
        dialogBuilder.setTitle((CharSequence) "Image");
        View dialogView = requireActivity().getLayoutInflater().inflate(R.layout.dialog_image_fragment, null);
        ((TextView) dialogView.findViewById(R.id.textview_imageurl)).setText(context.getData(6));
        String str = "Cancel";
        dialogBuilder.setView(dialogView).setPositiveButton((CharSequence) "Open link in Browser", context.imageurlDialogHandler).setNegativeButton((CharSequence) str, context.imageurlDialogHandler);
        return dialogBuilder;
    }

    private Builder createSellersDialog() {
        CommodityEntryActivity context = (CommodityEntryActivity) getActivity();
        Builder dialogBuilder = new Builder(context);
        dialogBuilder.setTitle((CharSequence) "Seller Name");
        View dialogView = requireActivity().getLayoutInflater().inflate(R.layout.dialog_seller_name, null);
        ((EditText) dialogView.findViewById(R.id.sellerEditText)).setText(context.getData(7));
        String str = "Cancel";
        dialogBuilder.setView(dialogView).setPositiveButton((CharSequence) "OK", context.sellerDialogHandler).setNegativeButton((CharSequence) str, context.sellerDialogHandler);
        return dialogBuilder;
    }
}
