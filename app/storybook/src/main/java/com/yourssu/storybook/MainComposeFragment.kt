package com.yourssu.storybook

class MainComposeFragment : BaseFragment() {
    companion object {
        @JvmStatic
        fun newInstance() = MainComposeFragment()
    }

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View = ComposeView(requireContext()).apply {
//        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
//        setContent {
//            YdsTheme {
//                Text("Hello Jetpack Compose!")
//            }
//        }
//    }
}