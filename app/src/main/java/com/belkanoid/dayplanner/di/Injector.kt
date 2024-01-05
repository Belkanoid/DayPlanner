package com.belkanoid.dayplanner.di


import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.belkanoid.dayplanner.presentation.PlannerApplication
import com.belkanoid.dayplanner.presentation.ViewModelFactory
import com.belkanoid.dayplanner.presentation.screens.detailedEvent.DetailedEventFragment
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

inline fun <reified T : ViewModel> Fragment.injectViewModel(crossinline initial: () -> ViewModelFactory) =
    ReadOnlyProperty<Fragment, T> { _, _ ->
        val factory = initial()
        ViewModelProvider(this@injectViewModel, factory)[T::class.java]
    }


fun Fragment.injectComponent() = ReadOnlyProperty<Fragment, PlannerComponent> { _, _ ->
    (this.requireActivity().application as PlannerApplication).component
}

fun DetailedEventFragment.injectSubcomponent(initial: () -> Int) = ReadOnlyProperty<Fragment, DetailedFragmentSubcomponent> { _, _ ->
    val id = initial()
    (this.requireActivity().application as PlannerApplication).component
        .detailedFragmentComponentFactory()
        .create(id)
}

inline fun <T: ViewBinding> Fragment.injectBinding(noinline factory: (View) -> T) =
    FragmentViewBindingDelegate(this, factory)

class FragmentViewBindingDelegate<T>(
    private val fragment: Fragment,
    private val factory: (View) -> T

) : ReadOnlyProperty<Fragment, T>,
    DefaultLifecycleObserver
{
    private var binding: T? = null
    override fun getValue(thisRef: Fragment, property: KProperty<*>) =
         binding ?: factory(fragment.requireView()).also {
            if (fragment.viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
                fragment.viewLifecycleOwner.lifecycle.addObserver(this)
                binding = it
            } else {
                throw UninitializedPropertyAccessException("${fragment::class.java.simpleName} binding is uninitialized")
            }
        }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        binding = null
    }

}

