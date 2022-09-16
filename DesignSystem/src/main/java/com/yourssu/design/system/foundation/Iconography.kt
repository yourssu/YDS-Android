package com.yourssu.design.system.foundation

import androidx.annotation.DrawableRes
import androidx.annotation.IntDef
import com.yourssu.design.R

object Icon {
    @Retention(AnnotationRetention.SOURCE)
    @IntDef(value = [
        ic_adbadge_filled,
        ic_adbadge_line,
        ic_arrow_down_line,
        ic_arrow_left_line,
        ic_arrow_right_line,
        ic_arrow_up_line,
        ic_bell_filled,
        ic_bell_line,
        ic_bellmute_line,
        ic_blockuser_line,
        ic_board_filled,
        ic_board_line,
        ic_book_filled,
        ic_book_line,
        ic_calendar_filled,
        ic_calendar_line,
        ic_camera_filled,
        ic_camera_line,
        ic_cameracircle_line,
        ic_check_line,
        ic_checkcircle_filled,
        ic_checkcircle_line,
        ic_clip_line,
        ic_comment_filled,
        ic_comment_line,
        ic_dotbadge_line,
        ic_dots_horizontal_line,
        ic_dots_vertical_line,
        ic_download_line,
        ic_emojiadd_line,
        ic_eyeclosed_line,
        ic_eyeopen_line,
        ic_food_filled,
        ic_food_line,
        ic_foodcalendar_filled,
        ic_foodcalendar_line,
        ic_ground_filled,
        ic_ground_line,
        ic_home_filled,
        ic_home_line,
        ic_list_line,
        ic_lock_filled,
        ic_lock_line,
        ic_new_filled,
        ic_new_line,
        ic_notice_filled,
        ic_notice_line,
        ic_pen_filled,
        ic_pen_line,
        ic_person_filled,
        ic_person_line,
        ic_personcircle_line,
        ic_picture_filled,
        ic_picture_line,
        ic_pin_filled,
        ic_pin_line,
        ic_playcircle_filled,
        ic_playcircle_line,
        ic_plus_line,
        ic_rank_filled,
        ic_rank_line,
        ic_recomment_line,
        ic_refresh_line,
        ic_savecircle_filled,
        ic_savecircle_line,
        ic_schoolcalendar_filled,
        ic_schoolcalendar_line,
        ic_search_line,
        ic_sharecircle_filled,
        ic_sharecircle_line,
        ic_star_filled,
        ic_star_line,
        ic_thumb_down_filled,
        ic_thumb_down_line,
        ic_thumb_up_filled,
        ic_thumb_up_line,
        ic_timecalendar_filled,
        ic_timecalendar_line,
        ic_trashcan_filled,
        ic_trashcan_line,
        ic_warningcircle_filled,
        ic_warningcircle_line,
        ic_x_line,
        ic_xcircle_filled
    ])
    annotation class Iconography

    const val ic_adbadge_filled = 0
    const val ic_adbadge_line = 1
    const val ic_arrow_down_line = 2
    const val ic_arrow_left_line = 3
    const val ic_arrow_right_line = 4
    const val ic_arrow_up_line = 5
    const val ic_bell_filled = 6
    const val ic_bell_line = 7
    const val ic_bellmute_line = 8
    const val ic_blockuser_line = 9
    const val ic_board_filled = 10
    const val ic_board_line = 11
    const val ic_book_filled = 12
    const val ic_book_line = 13
    const val ic_calendar_filled = 14
    const val ic_calendar_line = 15
    const val ic_camera_filled = 16
    const val ic_camera_line = 17
    const val ic_cameracircle_line = 18
    const val ic_check_line = 19
    const val ic_checkcircle_filled = 20
    const val ic_checkcircle_line = 21
    const val ic_clip_line = 22
    const val ic_comment_filled = 23
    const val ic_comment_line = 24
    const val ic_dotbadge_line = 25
    const val ic_dots_horizontal_line = 26
    const val ic_dots_vertical_line = 27
    const val ic_download_line = 28
    const val ic_emojiadd_line = 29
    const val ic_eyeclosed_line = 30
    const val ic_eyeopen_line = 31
    const val ic_food_filled = 32
    const val ic_food_line = 33
    const val ic_foodcalendar_filled = 34
    const val ic_foodcalendar_line = 35
    const val ic_ground_filled = 36
    const val ic_ground_line = 37
    const val ic_home_filled = 38
    const val ic_home_line = 39
    const val ic_list_line = 40
    const val ic_lock_filled = 41
    const val ic_lock_line = 42
    const val ic_new_filled = 43
    const val ic_new_line = 44
    const val ic_notice_filled = 45
    const val ic_notice_line = 46
    const val ic_pen_filled = 47
    const val ic_pen_line = 48
    const val ic_person_filled = 49
    const val ic_person_line = 50
    const val ic_personcircle_line = 51
    const val ic_picture_filled = 52
    const val ic_picture_line = 53
    const val ic_pin_filled = 54
    const val ic_pin_line = 55
    const val ic_playcircle_filled = 56
    const val ic_playcircle_line = 57
    const val ic_plus_line = 58
    const val ic_rank_filled = 59
    const val ic_rank_line = 60
    const val ic_recomment_line = 61
    const val ic_refresh_line = 62
    const val ic_savecircle_filled = 63
    const val ic_savecircle_line = 64
    const val ic_schoolcalendar_filled = 65
    const val ic_schoolcalendar_line = 66
    const val ic_search_line = 67
    const val ic_sharecircle_filled = 68
    const val ic_sharecircle_line = 69
    const val ic_star_filled = 70
    const val ic_star_line = 71
    const val ic_thumb_down_filled = 72
    const val ic_thumb_down_line = 73
    const val ic_thumb_up_filled = 74
    const val ic_thumb_up_line = 75
    const val ic_timecalendar_filled = 76
    const val ic_timecalendar_line = 77
    const val ic_trashcan_filled = 78
    const val ic_trashcan_line = 79
    const val ic_warningcircle_filled = 80
    const val ic_warningcircle_line = 81
    const val ic_x_line = 82
    const val ic_xcircle_filled= 83

    @DrawableRes
    fun getIconDrawable(@Iconography icon: Int): Int {
        return when (icon) {
            ic_adbadge_filled -> R.drawable.ic_adbadge_filled
            ic_adbadge_line -> R.drawable.ic_adbadge_line
            ic_arrow_down_line -> R.drawable.ic_arrow_down_line
            ic_arrow_left_line -> R.drawable.ic_arrow_left_line
            ic_arrow_right_line -> R.drawable.ic_arrow_right_line
            ic_arrow_up_line -> R.drawable.ic_arrow_up_line
            ic_bell_filled -> R.drawable.ic_bell_filled
            ic_bell_line -> R.drawable.ic_bell_line
            ic_bellmute_line -> R.drawable.ic_bellmute_line
            ic_blockuser_line -> R.drawable.ic_blockuser_line
            ic_board_filled -> R.drawable.ic_board_filled
            ic_board_line -> R.drawable.ic_board_line
            ic_book_filled -> R.drawable.ic_book_filled
            ic_book_line -> R.drawable.ic_book_line
            ic_calendar_filled -> R.drawable.ic_calendar_filled
            ic_calendar_line -> R.drawable.ic_calendar_line
            ic_camera_filled -> R.drawable.ic_camera_filled
            ic_camera_line -> R.drawable.ic_camera_line
            ic_cameracircle_line -> R.drawable.ic_cameracircle_line
            ic_check_line -> R.drawable.ic_check_line
            ic_checkcircle_filled -> R.drawable.ic_checkcircle_filled
            ic_checkcircle_line -> R.drawable.ic_checkcircle_line
            ic_clip_line -> R.drawable.ic_clip_line
            ic_comment_filled -> R.drawable.ic_comment_filled
            ic_comment_line -> R.drawable.ic_comment_line
            ic_dotbadge_line -> R.drawable.ic_dotbadge_line
            ic_dots_horizontal_line -> R.drawable.ic_dots_horizontal_line
            ic_dots_vertical_line -> R.drawable.ic_dots_vertical_line
            ic_download_line -> R.drawable.ic_download_line
            ic_emojiadd_line -> R.drawable.ic_emojiadd_line
            ic_eyeclosed_line -> R.drawable.ic_eyeclosed_line
            ic_eyeopen_line -> R.drawable.ic_eyeopen_line
            ic_food_filled -> R.drawable.ic_food_filled
            ic_food_line -> R.drawable.ic_food_line
            ic_foodcalendar_filled -> R.drawable.ic_foodcalendar_filled
            ic_foodcalendar_line -> R.drawable.ic_foodcalendar_line
            ic_ground_filled -> R.drawable.ic_ground_filled
            ic_ground_line -> R.drawable.ic_ground_line
            ic_home_filled -> R.drawable.ic_home_filled
            ic_home_line -> R.drawable.ic_home_line
            ic_list_line -> R.drawable.ic_list_line
            ic_lock_filled -> R.drawable.ic_lock_filled
            ic_lock_line -> R.drawable.ic_lock_line
            ic_new_filled -> R.drawable.ic_new_filled
            ic_new_line -> R.drawable.ic_new_line
            ic_notice_filled -> R.drawable.ic_notice_filled
            ic_notice_line -> R.drawable.ic_notice_line
            ic_pen_filled -> R.drawable.ic_pen_filled
            ic_pen_line -> R.drawable.ic_pen_line
            ic_person_filled -> R.drawable.ic_person_filled
            ic_person_line -> R.drawable.ic_person_line
            ic_personcircle_line -> R.drawable.ic_personcircle_line
            ic_picture_filled -> R.drawable.ic_picture_filled
            ic_picture_line -> R.drawable.ic_picture_line
            ic_pin_filled -> R.drawable.ic_pin_filled
            ic_pin_line -> R.drawable.ic_pin_line
            ic_playcircle_filled -> R.drawable.ic_playcircle_filled
            ic_playcircle_line -> R.drawable.ic_playcircle_line
            ic_plus_line -> R.drawable.ic_plus_line
            ic_rank_filled -> R.drawable.ic_rank_filled
            ic_rank_line -> R.drawable.ic_rank_line
            ic_recomment_line -> R.drawable.ic_recomment_line
            ic_refresh_line -> R.drawable.ic_refresh_line
            ic_savecircle_filled -> R.drawable.ic_savecircle_filled
            ic_savecircle_line -> R.drawable.ic_savecircle_line
            ic_schoolcalendar_filled -> R.drawable.ic_schoolcalendar_filled
            ic_schoolcalendar_line -> R.drawable.ic_schoolcalendar_line
            ic_search_line -> R.drawable.ic_search_line
            ic_sharecircle_filled -> R.drawable.ic_sharecircle_filled
            ic_sharecircle_line -> R.drawable.ic_sharecircle_line
            ic_star_filled -> R.drawable.ic_star_filled
            ic_star_line -> R.drawable.ic_star_line
            ic_thumb_down_filled -> R.drawable.ic_thumb_down_filled
            ic_thumb_down_line -> R.drawable.ic_thumb_down_line
            ic_thumb_up_filled -> R.drawable.ic_thumb_up_filled
            ic_thumb_up_line -> R.drawable.ic_thumb_up_line
            ic_timecalendar_filled -> R.drawable.ic_timecalendar_filled
            ic_timecalendar_line -> R.drawable.ic_timecalendar_line
            ic_trashcan_filled -> R.drawable.ic_trashcan_filled
            ic_trashcan_line -> R.drawable.ic_trashcan_line
            ic_warningcircle_filled -> R.drawable.ic_warningcircle_filled
            ic_warningcircle_line -> R.drawable.ic_warningcircle_line
            ic_x_line -> R.drawable.ic_x_line
            ic_xcircle_filled -> R.drawable.ic_xcircle_filled
            else -> R.drawable.ic_adbadge_filled
        }
    }

    fun getName(@Iconography value: Int): String {
        return when (value) {
            ic_adbadge_filled -> "ic_adbadge_filled"
            ic_adbadge_line -> "ic_adbadge_line"
            ic_arrow_down_line -> "ic_arrow_down_line"
            ic_arrow_left_line -> "ic_arrow_left_line"
            ic_arrow_right_line -> "ic_arrow_right_line"
            ic_arrow_up_line -> "ic_arrow_up_line"
            ic_bell_filled -> "ic_bell_filled"
            ic_bell_line -> "ic_bell_line"
            ic_bellmute_line -> "ic_bellmute_line"
            ic_blockuser_line -> "ic_blockuser_line"
            ic_board_filled -> "ic_board_filled"
            ic_board_line -> "ic_board_line"
            ic_book_filled -> "ic_book_filled"
            ic_book_line -> "ic_book_line"
            ic_calendar_filled -> "ic_calendar_filled"
            ic_calendar_line -> "ic_calendar_line"
            ic_camera_filled -> "ic_camera_filled"
            ic_camera_line -> "ic_camera_line"
            ic_cameracircle_line -> "ic_cameracircle_line"
            ic_check_line -> "ic_check_line"
            ic_checkcircle_filled -> "ic_checkcircle_filled"
            ic_checkcircle_line -> "ic_checkcircle_line"
            ic_clip_line -> "ic_clip_line"
            ic_comment_filled -> "ic_comment_filled"
            ic_comment_line -> "ic_comment_line"
            ic_dotbadge_line -> "ic_dotbadge_line"
            ic_dots_horizontal_line -> "ic_dots_horizontal_line"
            ic_dots_vertical_line -> "ic_dots_vertical_line"
            ic_download_line -> "ic_download_line"
            ic_emojiadd_line -> "ic_emojiadd_line"
            ic_eyeclosed_line -> "ic_eyeclosed_line"
            ic_eyeopen_line -> "ic_eyeopen_line"
            ic_food_filled -> "ic_food_filled"
            ic_food_line -> "ic_food_line"
            ic_foodcalendar_filled -> "ic_foodcalendar_filled"
            ic_foodcalendar_line -> "ic_foodcalendar_line"
            ic_ground_filled -> "ic_ground_filled"
            ic_ground_line -> "ic_ground_line"
            ic_home_filled -> "ic_home_filled"
            ic_home_line -> "ic_home_line"
            ic_list_line -> "ic_list_line"
            ic_lock_filled -> "ic_lock_filled"
            ic_lock_line -> "ic_lock_line"
            ic_new_filled -> "ic_new_filled"
            ic_new_line -> "ic_new_line"
            ic_notice_filled -> "ic_notice_filled"
            ic_notice_line -> "ic_notice_line"
            ic_pen_filled -> "ic_pen_filled"
            ic_pen_line -> "ic_pen_line"
            ic_person_filled -> "ic_person_filled"
            ic_person_line -> "ic_person_line"
            ic_personcircle_line -> "ic_personcircle_line"
            ic_picture_filled -> "ic_picture_filled"
            ic_picture_line -> "ic_picture_line"
            ic_pin_filled -> "ic_pin_filled"
            ic_pin_line -> "ic_pin_line"
            ic_playcircle_filled -> "ic_playcircle_filled"
            ic_playcircle_line -> "ic_playcircle_line"
            ic_plus_line -> "ic_plus_line"
            ic_rank_filled -> "ic_rank_filled"
            ic_rank_line -> "ic_rank_line"
            ic_recomment_line -> "ic_recomment_line"
            ic_refresh_line -> "ic_refresh_line"
            ic_savecircle_filled -> "ic_savecircle_filled"
            ic_savecircle_line -> "ic_savecircle_line"
            ic_schoolcalendar_filled -> "ic_schoolcalendar_filled"
            ic_schoolcalendar_line -> "ic_schoolcalendar_line"
            ic_search_line -> "ic_search_line"
            ic_sharecircle_filled -> "ic_sharecircle_filled"
            ic_sharecircle_line -> "ic_sharecircle_line"
            ic_star_filled -> "ic_star_filled"
            ic_star_line -> "ic_star_line"
            ic_thumb_down_filled -> "ic_thumb_down_filled"
            ic_thumb_down_line -> "ic_thumb_down_line"
            ic_thumb_up_filled -> "ic_thumb_up_filled"
            ic_thumb_up_line -> "ic_thumb_up_line"
            ic_timecalendar_filled -> "ic_timecalendar_filled"
            ic_timecalendar_line -> "ic_timecalendar_line"
            ic_trashcan_filled -> "ic_trashcan_filled"
            ic_trashcan_line -> "ic_trashcan_line"
            ic_warningcircle_filled -> "ic_warningcircle_filled"
            ic_warningcircle_line -> "ic_warningcircle_line"
            ic_x_line -> "ic_x_line"
            ic_xcircle_filled -> "ic_xcircle_filled"
            else -> "ic_adbadge_filled"
        }
    }

    fun getValueByName(value: String): Int {
        return when (value) {
            "ic_adbadge_filled" -> ic_adbadge_filled
            "ic_adbadge_line" -> ic_adbadge_line
            "ic_arrow_down_line" -> ic_arrow_down_line
            "ic_arrow_left_line" -> ic_arrow_left_line
            "ic_arrow_right_line" -> ic_arrow_right_line
            "ic_arrow_up_line" -> ic_arrow_up_line
            "ic_bell_filled" -> ic_bell_filled
            "ic_bell_line" -> ic_bell_line
            "ic_bellmute_line" -> ic_bellmute_line
            "ic_blockuser_line" -> ic_blockuser_line
            "ic_board_filled" -> ic_board_filled
            "ic_board_line" -> ic_board_line
            "ic_book_filled" -> ic_book_filled
            "ic_book_line" -> ic_book_line
            "ic_calendar_filled" -> ic_calendar_filled
            "ic_calendar_line" -> ic_calendar_line
            "ic_camera_filled" -> ic_camera_filled
            "ic_camera_line" -> ic_camera_line
            "ic_cameracircle_line" -> ic_cameracircle_line
            "ic_check_line" -> ic_check_line
            "ic_checkcircle_filled" -> ic_checkcircle_filled
            "ic_checkcircle_line" -> ic_checkcircle_line
            "ic_clip_line" -> ic_clip_line
            "ic_comment_filled" -> ic_comment_filled
            "ic_comment_line" -> ic_comment_line
            "ic_dotbadge_line" -> ic_dotbadge_line
            "ic_dots_horizontal_line" -> ic_dots_horizontal_line
            "ic_dots_vertical_line" -> ic_dots_vertical_line
            "ic_download_line" -> ic_download_line
            "ic_emojiadd_line" -> ic_emojiadd_line
            "ic_eyeclosed_line" -> ic_eyeclosed_line
            "ic_eyeopen_line" -> ic_eyeopen_line
            "ic_food_filled" -> ic_food_filled
            "ic_food_line" -> ic_food_line
            "ic_foodcalendar_filled" -> ic_foodcalendar_filled
            "ic_foodcalendar_line" -> ic_foodcalendar_line
            "ic_ground_filled" -> ic_ground_filled
            "ic_ground_line" -> ic_ground_line
            "ic_home_filled" -> ic_home_filled
            "ic_home_line" -> ic_home_line
            "ic_list_line" -> ic_list_line
            "ic_lock_filled" -> ic_lock_filled
            "ic_lock_line" -> ic_lock_line
            "ic_new_filled" -> ic_new_filled
            "ic_new_line" -> ic_new_line
            "ic_notice_filled" -> ic_notice_filled
            "ic_notice_line" -> ic_notice_line
            "ic_pen_filled" -> ic_pen_filled
            "ic_pen_line" -> ic_pen_line
            "ic_person_filled" -> ic_person_filled
            "ic_person_line" -> ic_person_line
            "ic_personcircle_line" -> ic_personcircle_line
            "ic_picture_filled" -> ic_picture_filled
            "ic_picture_line" -> ic_picture_line
            "ic_pin_filled" -> ic_pin_filled
            "ic_pin_line" -> ic_pin_line
            "ic_playcircle_filled" -> ic_playcircle_filled
            "ic_playcircle_line" -> ic_playcircle_line
            "ic_plus_line" -> ic_plus_line
            "ic_rank_filled" -> ic_rank_filled
            "ic_rank_line" -> ic_rank_line
            "ic_recomment_line" -> ic_recomment_line
            "ic_refresh_line" -> ic_refresh_line
            "ic_savecircle_filled" -> ic_savecircle_filled
            "ic_savecircle_line" -> ic_savecircle_line
            "ic_schoolcalendar_filled" -> ic_schoolcalendar_filled
            "ic_schoolcalendar_line" -> ic_schoolcalendar_line
            "ic_search_line" -> ic_search_line
            "ic_sharecircle_filled" -> ic_sharecircle_filled
            "ic_sharecircle_line" -> ic_sharecircle_line
            "ic_star_filled" -> ic_star_filled
            "ic_star_line" -> ic_star_line
            "ic_thumb_down_filled" -> ic_thumb_down_filled
            "ic_thumb_down_line" -> ic_thumb_down_line
            "ic_thumb_up_filled" -> ic_thumb_up_filled
            "ic_thumb_up_line" -> ic_thumb_up_line
            "ic_timecalendar_filled" -> ic_timecalendar_filled
            "ic_timecalendar_line" -> ic_timecalendar_line
            "ic_trashcan_filled" -> ic_trashcan_filled
            "ic_trashcan_line" -> ic_trashcan_line
            "ic_warningcircle_filled" -> ic_warningcircle_filled
            "ic_warningcircle_line" -> ic_warningcircle_line
            "ic_x_line" -> ic_x_line
            "ic_xcircle_filled" -> ic_xcircle_filled
            else -> ic_adbadge_filled
        }
    }

    fun getList(): List<Int> {
        return listOf(
            ic_adbadge_filled,
            ic_adbadge_line,
            ic_arrow_down_line,
            ic_arrow_left_line,
            ic_arrow_right_line,
            ic_arrow_up_line,
            ic_bell_filled,
            ic_bell_line,
            ic_bellmute_line,
            ic_blockuser_line,
            ic_board_filled,
            ic_board_line,
            ic_book_filled,
            ic_book_line,
            ic_calendar_filled,
            ic_calendar_line,
            ic_camera_filled,
            ic_camera_line,
            ic_cameracircle_line,
            ic_check_line,
            ic_checkcircle_filled,
            ic_checkcircle_line,
            ic_clip_line,
            ic_comment_filled,
            ic_comment_line,
            ic_dotbadge_line,
            ic_dots_horizontal_line,
            ic_dots_vertical_line,
            ic_download_line,
            ic_emojiadd_line,
            ic_eyeclosed_line,
            ic_eyeopen_line,
            ic_food_filled,
            ic_food_line,
            ic_foodcalendar_filled,
            ic_foodcalendar_line,
            ic_ground_filled,
            ic_ground_line,
            ic_home_filled,
            ic_home_line,
            ic_list_line,
            ic_lock_filled,
            ic_lock_line,
            ic_new_filled,
            ic_new_line,
            ic_notice_filled,
            ic_notice_line,
            ic_pen_filled,
            ic_pen_line,
            ic_person_filled,
            ic_person_line,
            ic_personcircle_line,
            ic_picture_filled,
            ic_picture_line,
            ic_pin_filled,
            ic_pin_line,
            ic_playcircle_filled,
            ic_playcircle_line,
            ic_plus_line,
            ic_rank_filled,
            ic_rank_line,
            ic_recomment_line,
            ic_refresh_line,
            ic_savecircle_filled,
            ic_savecircle_line,
            ic_schoolcalendar_filled,
            ic_schoolcalendar_line,
            ic_search_line,
            ic_sharecircle_filled,
            ic_sharecircle_line,
            ic_star_filled,
            ic_star_line,
            ic_thumb_down_filled,
            ic_thumb_down_line,
            ic_thumb_up_filled,
            ic_thumb_up_line,
            ic_timecalendar_filled,
            ic_timecalendar_line,
            ic_trashcan_filled,
            ic_trashcan_line,
            ic_warningcircle_filled,
            ic_warningcircle_line,
            ic_x_line,
            ic_xcircle_filled
        )
    }
}