/*
package com.galaxy_techno.uKnow.presentation.extension

import android.view.View
import android.view.MotionEvent
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.widget.TextView
import android.view.LayoutInflater
import android.os.Build
import androidx.annotation.RequiresApi
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.annotation.Nullable
import android.view.ViewGroup
import androidx.annotation.IdRes
import android.widget.LinearLayout
import androidx.appcompat.widget.ViewUtils
import androidx.core.view.ViewCompat.generateViewId
import com.galaxy_techno.seller.R


interface RadioCheckable : Checkable {
    fun addOnCheckChangeListener(onCheckedChangeListener: OnCheckedChangeListener?)
    fun removeOnCheckChangeListener(onCheckedChangeListener: OnCheckedChangeListener?)
    interface OnCheckedChangeListener {
        fun onCheckedChanged(radioGroup: View?, isChecked: Boolean)
    }
}

interface Checkable {
    var isChecked: Boolean
    fun toggle()
}

open class PresetValueButton : RelativeLayout, RadioCheckable {
    // Views
    private var typeImage: ImageView? = null
    private var typeText: TextView? = null

    // Attribute Variables
    var textValue: String? = null
    var imageValue : Drawable? = null
    private var textColor = 0

    // Variables
    private var mInitialBackgroundDrawable: Drawable? = null
    private var mOnClickListener: OnClickListener? = null
    var onTouchListener: OnTouchListener? = null
        private set
    private var mChecked = false
    private val mOnCheckedChangeListeners: ArrayList<RadioCheckable.OnCheckedChangeListener?> =
        ArrayList()

    //================================================================================
    // Constructors
    //================================================================================
    constructor(context: Context?) : super(context) {
        setupView()
    }

    constructor(context: Context?, attrs: AttributeSet) : super(context, attrs) {
        parseAttributes(attrs)
        setupView()
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    constructor(context: Context?, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        parseAttributes(attrs)
        setupView()
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context?,
        attrs: AttributeSet,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        parseAttributes(attrs)
        setupView()
    }

    //================================================================================
    // Init & inflate methods
    //================================================================================
    private fun parseAttributes(attrs: AttributeSet) {
        val a = context.obtainStyledAttributes(
            attrs,
            R.styleable.PresentValueButton, 0, 0
        )
        val resources: Resources = context.resources
        try {
            textValue = a.getString(R.styleable.PresetValueButton_presetButtonValueText)
            textColor = a.getColor(
                R.styleable.PresetValueButton_presetButtonValueTextColor,
                resources.getColor(R.color.black)
            )

        } finally {
            a.recycle()
        }
    }

    // Template method
    private fun setupView() {
        inflateView()
        bindView()
        setCustomTouchListener()
    }

    private fun inflateView() {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.layout_type_radio, this, true)
        typeImage = findViewById<View>(R.id.img) as ImageView
        typeText = findViewById<View>(R.id.tv) as TextView
        mInitialBackgroundDrawable = background
    }

    private fun bindView() {
        typeImage!!.setImageDrawable(imageValue)
        typeText!!.text = textValue
    }

    //================================================================================
    // Overriding default behavior
    //================================================================================
    override fun setOnClickListener(@Nullable l: OnClickListener?) {
        mOnClickListener = l
    }

    private fun setCustomTouchListener() {
        super.setOnTouchListener(TouchListener())
    }

    override fun setOnTouchListener(onTouchListener: OnTouchListener) {
        this.onTouchListener = onTouchListener
    }

    private fun onTouchDown(motionEvent: MotionEvent) {
        isChecked = true
    }

    private fun onTouchUp(motionEvent: MotionEvent) {
        // Handle user defined click listeners
        if (mOnClickListener != null) {
            mOnClickListener!!.onClick(this)
        }
    }

    //================================================================================
    // Public methods
    //================================================================================
    fun setCheckedState() {
        setBackgroundResource(R.drawable.custom_radio_selected)
    }

    fun setNormalState() {
        setBackgroundDrawable(mInitialBackgroundDrawable)
    }

    //================================================================================
    // Checkable implementation
    //================================================================================
    override var isChecked: Boolean
        get() = mChecked
        set(checked) {
            if (mChecked != checked) {
                mChecked = checked
                if (mOnCheckedChangeListeners.isNotEmpty()) {
                    for (i in 0 until mOnCheckedChangeListeners.size) {
                        mOnCheckedChangeListeners[i]!!.onCheckedChanged(this, mChecked)
                    }
                }
                if (mChecked) {
                    setCheckedState()
                } else {
                    setNormalState()
                }
            }
        }

    override fun toggle() {
        isChecked = !mChecked
    }

    override fun addOnCheckChangeListener(onCheckedChangeListener: RadioCheckable.OnCheckedChangeListener?) {
        mOnCheckedChangeListeners.add(onCheckedChangeListener)
    }

    override fun removeOnCheckChangeListener(onCheckedChangeListener: RadioCheckable.OnCheckedChangeListener?) {
        mOnCheckedChangeListeners.remove(onCheckedChangeListener)
    }

    //================================================================================
    // Inner classes
    //================================================================================
    private inner class TouchListener : OnTouchListener {
        override fun onTouch(v: View, event: MotionEvent): Boolean {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> onTouchDown(event)
                MotionEvent.ACTION_UP -> onTouchUp(event)
            }
            if (onTouchListener != null) {
                onTouchListener!!.onTouch(v, event)
            }
            return true
        }
    }

    companion object {
        // Constants
        const val DEFAULT_TEXT_COLOR: Int = Color.TRANSPARENT
    }
}


class PresetRadioGroup : LinearLayout {
    // Attribute Variables
    private var mCheckedId = NO_ID
    private var mProtectFromCheckedChange = false

    //================================================================================
    // Public methods
    //================================================================================
    // Variables
    var onCheckedChangeListener: OnCheckedChangeListener? = null
    private val mChildViewsMap: HashMap<Int, View> = HashMap()
    private var mPassThroughListener: PassThroughHierarchyChangeListener? = null
    private var mChildOnCheckedChangeListener: RadioCheckable.OnCheckedChangeListener? = null

    //================================================================================
    // Constructors
    //================================================================================
    constructor(context: Context?) : super(context) {
        setupView()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        parseAttributes(attrs)
        setupView()
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        parseAttributes(attrs)
        setupView()
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        parseAttributes(attrs)
        setupView()
    }

    //================================================================================
    // Init & inflate methods
    //================================================================================
    private fun parseAttributes(attrs: AttributeSet?) {
        val a = context.obtainStyledAttributes(
            attrs,
            R.styleable.PresetRadioGroup, 0, 0
        )
        mCheckedId = try {
            a.getResourceId(R.styleable.PresetRadioGroup_presetRadioCheckedId, NO_ID)
        } finally {
            a.recycle()
        }
    }

    // Template method
    private fun setupView() {
        mChildOnCheckedChangeListener = CheckedStateTracker()
        mPassThroughListener = PassThroughHierarchyChangeListener()
        super.setOnHierarchyChangeListener(mPassThroughListener)
    }

    //================================================================================
    // Overriding default behavior
    //================================================================================
    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams) {
        if (child is RadioCheckable) {
            val button = child as RadioCheckable
            if (button.isChecked) {
                mProtectFromCheckedChange = true
                if (mCheckedId != NO_ID) {
                    setCheckedStateForView(mCheckedId, false)
                }
                mProtectFromCheckedChange = false
                setCheckedId(child.id, true)
            }
        }
        super.addView(child, index, params)
    }

    override fun setOnHierarchyChangeListener(listener: OnHierarchyChangeListener) {
        // the user listener is delegated to our pass-through listener
        mPassThroughListener!!.mOnHierarchyChangeListener = listener
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        // checks the appropriate radio button as requested in the XML file
        if (mCheckedId != NO_ID) {
            mProtectFromCheckedChange = true
            setCheckedStateForView(mCheckedId, true)
            mProtectFromCheckedChange = false
            setCheckedId(mCheckedId, true)
        }
    }

    private fun setCheckedId(@IdRes id: Int, isChecked: Boolean) {
        mCheckedId = id
        if (onCheckedChangeListener != null) {
            onCheckedChangeListener!!.onCheckedChanged(
                this,
                mChildViewsMap[id], isChecked, mCheckedId
            )
        }
    }

    override fun checkLayoutParams(p: ViewGroup.LayoutParams): Boolean {
        return p is LayoutParams
    }

    fun clearCheck() {
        check(NO_ID)
    }

    fun check(@IdRes id: Int) {
        // don't even bother
        if (id != NO_ID && id == mCheckedId) {
            return
        }
        if (mCheckedId != NO_ID) {
            setCheckedStateForView(mCheckedId, false)
        }
        if (id != NO_ID) {
            setCheckedStateForView(id, true)
        }
        setCheckedId(id, true)
    }

    private fun setCheckedStateForView(viewId: Int, checked: Boolean) {
        var checkedView: View?
        checkedView = mChildViewsMap[viewId]
        if (checkedView == null) {
            checkedView = findViewById(viewId)
            if (checkedView != null) {
                mChildViewsMap[viewId] = checkedView
            }
        }
        if (checkedView != null && checkedView is RadioCheckable) {
            (checkedView as RadioCheckable).isChecked = checked
        }
    }

    override fun generateLayoutParams(attrs: AttributeSet): LayoutParams {
        return LayoutParams(context, attrs)
    }

    //================================================================================
    // Nested classes
    //================================================================================
    interface OnCheckedChangeListener {
        fun onCheckedChanged(
            radioGroup: View?,
            radioButton: View?,
            isChecked: Boolean,
            checkedId: Int
        )
    }

    //================================================================================
    // Inner classes
    //================================================================================
    private inner class CheckedStateTracker : RadioCheckable.OnCheckedChangeListener {
        override fun onCheckedChanged(buttonView: View?, isChecked: Boolean) {
            // prevents from infinite recursion
            if (mProtectFromCheckedChange) {
                return
            }
            mProtectFromCheckedChange = true
            if (mCheckedId != NO_ID) {
                setCheckedStateForView(mCheckedId, false)
            }
            mProtectFromCheckedChange = false
            val id = buttonView!!.id
            setCheckedId(id, true)
        }
    }

    private inner class PassThroughHierarchyChangeListener :
        OnHierarchyChangeListener {
        var mOnHierarchyChangeListener: OnHierarchyChangeListener? = null

        override fun onChildViewAdded(parent: View, child: View) {
            if (parent === this@PresetRadioGroup && child is RadioCheckable) {
                var id = child.id
                // generates an id if it's missing
                if (id == NO_ID) {
                    id = ViewUtils.generateViewId()
                    child.id = id
                }
                (child as RadioCheckable).addOnCheckChangeListener(
                    mChildOnCheckedChangeListener
                )
                mChildViewsMap[id] = child
            }
            mOnHierarchyChangeListener?.onChildViewAdded(parent, child)
        }

        override fun onChildViewRemoved(parent: View, child: View) {
            if (parent === this@PresetRadioGroup && child is RadioCheckable) {
                (child as RadioCheckable).removeOnCheckChangeListener(mChildOnCheckedChangeListener)
            }
            mChildViewsMap.remove(child.id)
            mOnHierarchyChangeListener?.onChildViewRemoved(parent, child)
        }
    }
}
*/
