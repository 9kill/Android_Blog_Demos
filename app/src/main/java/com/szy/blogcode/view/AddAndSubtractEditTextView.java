package com.szy.blogcode.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szy.blogcode.R;


/**
 * 类描述：
 * 创建人：sunzhenyu
 * 创建时间：2016/1/9 18:55
 */
public class AddAndSubtractEditTextView extends LinearLayout {
    private static final String FLAG_NUMBER="0";
    private static final String FLAG_NUMBERDECIMAL="1";

    private EditText mEditText;
    private TextView add;
    private TextView subtract;

    private String inputType;

    private OnNumberChangeListener mOnNumberChangeListener;

    public void setOnNumberChangeListener(OnNumberChangeListener onNumberChangeListener) {
        mOnNumberChangeListener = onNumberChangeListener;
    }

    public AddAndSubtractEditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = null;
        try {
            a=getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.AddAndSubtractEditTextView, 0, 0);
            inputType= TextUtils.isEmpty(a.getString(R.styleable.AddAndSubtractEditTextView_inputTypeAccuracy))?"0":a.getString(R.styleable.AddAndSubtractEditTextView_inputTypeAccuracy);
            String unitName=TextUtils.isEmpty(a.getString(R.styleable.AddAndSubtractEditTextView_unitName))?"mmHg":a.getString(R.styleable.AddAndSubtractEditTextView_unitName);
            String text=TextUtils.isEmpty(a.getString(R.styleable.AddAndSubtractEditTextView_inputTextValue))?"0":a.getString(R.styleable.AddAndSubtractEditTextView_inputTextValue);
            int textColor=a.getColor(R.styleable.AddAndSubtractEditTextView_inputTextColor, Color.parseColor("#5CCE90"));
            float textSize=a.getDimension(R.styleable.AddAndSubtractEditTextView_inputTextSize, 18);
            LayoutInflater.from(getContext()).inflate(R.layout.layout_add_and_subtract, this);
            TextView unit= (TextView) findViewById(R.id.unit);
            add= (TextView) findViewById(R.id.add);
            subtract= (TextView) findViewById(R.id.subtract);
            mEditText= (EditText) findViewById(R.id.input);

            mEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s != null) {
                        mEditText.setSelection(s.toString().length());
                    }
                }
            });
            mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        String text = mEditText.getText().toString();
                        if (!TextUtils.isEmpty(text)) {
                            if (FLAG_NUMBER.equals(inputType)) {
                                int intNum;
                                if (text.contains(".")) {
                                    intNum = (int) Float.valueOf(text).floatValue();
                                } else {
                                    intNum = Integer.valueOf(text);
                                }
                                if (mOnNumberChangeListener != null) {
                                    mOnNumberChangeListener.OnSerach(intNum);
                                }
                            } else if (FLAG_NUMBERDECIMAL.equals(inputType)) {
                                float floatNum = Float.valueOf(String.format("%.1f", Float.valueOf(text)));
                                if (mOnNumberChangeListener != null) {
                                    mOnNumberChangeListener.OnSerach(floatNum);
                                }
                            }
                        }
                        hideSoftKeyBoard();
                        return true;
                    }
                    return false;
                }
            });

            if (FLAG_NUMBER.equals(inputType)){
                registerNumberUnitListener();
            }else if (FLAG_NUMBERDECIMAL.equals(inputType)){
                registerNumberDecimalUnitListener();
            }

            unit.setText(unitName);
            mEditText.setText(text);
            mEditText.setSelection(mEditText.getText().toString().length());
            mEditText.setTextColor(textColor);
            mEditText.setTextSize(textSize);

            hideSoftKeyBoard();
        }finally {
            a.recycle();
        }
    }

    public void hideSoftKeyBoard(){
        InputMethodManager imm= (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (getContext() instanceof Activity){
            Activity activity= (Activity) getContext();
            if (activity.getCurrentFocus()!=null){
                imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    private void registerNumberUnitListener(){
        add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String text=mEditText.getText().toString();
                int num;
                if (text.contains(".")) {
                    num= (int) Float.valueOf(text).floatValue();
                } else {
                    num=Integer.valueOf(text);
                }
                num++;
                mEditText.setText(Integer.toString(num));
                if (mOnNumberChangeListener!=null){
                    mOnNumberChangeListener.OnAdd(num);
                }
            }
        });
        subtract.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String text=mEditText.getText().toString();
                int num;
                if (text.contains(".")) {
                    num= (int) Float.valueOf(text).floatValue();
                } else {
                    num=Integer.valueOf(text);
                }
                if (num > 0) {
                    num--;
                }
                mEditText.setText(Integer.toString(num));
                if (mOnNumberChangeListener!=null){
                    mOnNumberChangeListener.OnSubtract(num);
                }
            }
        });
    }
    private void registerNumberDecimalUnitListener(){
        add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String formatSize= String.format("%.1f",Float.valueOf(mEditText.getText().toString()));
                float size_onetench=Float.valueOf(formatSize);

                size_onetench+=0.1f;
                mEditText.setText(String.format("%.1f", size_onetench));
                if (mOnNumberChangeListener!=null){
                    mOnNumberChangeListener.OnAdd(Float.valueOf(String.format("%.1f", size_onetench)));
                }
            }
        });
        subtract.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String formatSize= String.format("%.1f", Float.valueOf(mEditText.getText().toString()));
                float size_onetench=Float.valueOf(formatSize);
                if (size_onetench>0.0f){
                    size_onetench-=0.1f;
                }
                mEditText.setText(String.format("%.1f", size_onetench));
                if (mOnNumberChangeListener!=null){
                    mOnNumberChangeListener.OnSubtract(Float.valueOf(String.format("%.1f", size_onetench)));
                }
            }
        });
    }

    public interface OnNumberChangeListener{
        void OnAdd(int currentVale);
        void OnSubtract(int currentVale);
        void OnAdd(float currentVale);
        void OnSubtract(float currentVale);
        void OnSerach(int currentVale);
        void OnSerach(float currentVale);
    }

    public static class OnNumberChangeListenerAdapter implements OnNumberChangeListener{

        @Override
        public void OnAdd(int currentVale) {

        }

        @Override
        public void OnSubtract(int currentVale) {

        }

        @Override
        public void OnAdd(float currentVale) {

        }

        @Override
        public void OnSubtract(float currentVale) {

        }

        @Override
        public void OnSerach(int currentVale) {

        }

        @Override
        public void OnSerach(float currentVale) {

        }
    }

    public String getText(){
        return mEditText.getText().toString();
    }

    public void setText(String text){
        mEditText.setText(text);
    }

}
