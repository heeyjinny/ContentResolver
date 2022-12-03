package com.heeyjinny.contentresolver

import android.net.Uri
import android.provider.MediaStore

//1
//음원 데이터 관련 클래스
//음원 데이터에서 읽어올 프로퍼티 정의
//id : MediaStore가 음원을 구분하는 유니크 ID
//title : 음원 제목
//artist : 음원의 아티스트 명
//albumId : 앨범을 구분하는 ID
//duration : 음원 길이

//2
//음원프로퍼티 설정을 기준으로 클래스 파라미터 설정
class Music(id: String, title: String?, artist: String?, albumId: String?, duration: Long?) {

    //2-1
    //프로퍼티 생성
    var id: String = ""
    var title: String? = null
    var artist: String? = null
    var albumId: String? = null
    var duration: Long? = null

    //2-2
    //프로퍼티와 파라미터 연결
    init {
        this.id = id
        this.title = title
        this.artist = artist
        this.albumId = albumId
        this.duration = duration
    }

    //3
    //음원의 URI를 생성하는 메서드 getMusicUri() 정의
    //음원URI는 기본MediaStore의 주소와 음원ID를 조합해 만들기 때문에
    //메서드로 만들어 놓고 사용하는게 편리함
    fun getMusicUri(): Uri{
        //3-1
        //Uri.withAppendedPath()를 사용하여 음원URI 가져오기
        return Uri.withAppendedPath(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id)
    }

    //4
    //음원 파일별 썸네일URI 생성 메서드 정의
    fun  getAlbumUri(): Uri{
        //4-1
        //앨범아트(썸네일)의 URI문자열을 Uri.parse로 해석하여 URI생성
        return Uri.parse("content://media/external/audio/albumart/$albumId")
    }

    //5
    //음원 목록 화면 만들기
    //activity_main.xml 작성

}//Music