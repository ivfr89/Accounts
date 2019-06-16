package com.fernandez.players.presentation.customview

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.TextViewCompat
import com.fernandez.players.R

class TextViewLabel : ConstraintLayout
{

    private var txtLabel: TextView?=null
    private var txtContent: TextView?=null

    private var labelTitle: String?=null



    constructor(ctx: Context, attrs: AttributeSet, defStyle: Int): super(ctx, attrs, defStyle)
    {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view = inflater.inflate(R.layout.custom_textview_label, this)

        txtLabel = view.findViewById(R.id.txtLabel)
        txtContent = view.findViewById(R.id.txtContent)

        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.TextViewLabel, defStyle, 0)

        labelTitle = typeArray.getString(R.styleable.TextViewLabel_label)

        txtLabel?.text = labelTitle

        txtLabel?.apply {
            text = labelTitle
        }

        txtContent?.apply {
            TextViewCompat.setAutoSizeTextTypeWithDefaults(this,
                TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM)
        }

        this.visibility = View.GONE

        typeArray.recycle()
    }

    constructor(ctx: Context, attrs: AttributeSet): super(ctx, attrs)
    {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view = inflater.inflate(R.layout.custom_textview_label, this)

        txtLabel = view.findViewById(R.id.txtLabel)
        txtContent = view.findViewById(R.id.txtContent)

        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.TextViewLabel)

        labelTitle = typeArray.getString(R.styleable.TextViewLabel_label)



        txtLabel?.apply {
            text = labelTitle
        }

        txtContent?.apply {
            TextViewCompat.setAutoSizeTextTypeWithDefaults(this,
                TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM)
        }


        this.visibility = View.GONE


        typeArray.recycle()

    }

    constructor(ctx: Context): super(ctx)
    {

    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

    fun setTextLabel(content: String?)
    {
        txtContent?.text = content

        if(content.isNullOrEmpty())
            this.visibility = View.GONE
        else
            this.visibility = View.VISIBLE

    }




}