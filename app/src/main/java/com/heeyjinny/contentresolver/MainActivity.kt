package com.heeyjinny.contentresolver

import android.Manifest
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.heeyjinny.contentresolver.databinding.ActivityMainBinding

//1
//외부저장소인 MediaStore의 음원 접근을 위해
//Manifest.xml에 외부저장소 권한 명세

class MainActivity : AppCompatActivity() {

    //2
    //저장소 권한 런처 선언 변수 생성
    lateinit var storagePermission: ActivityResultLauncher<String>

    //뷰바인딩
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //4
        //registerForActivityResult로 런처를 생성하여 런처선언변수 storagePermission에 저장
        storagePermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            //4-1
            //런처선언이 승인 되었다면 앱실행 메서드 startProcess() 호출
            if (isGranted){
                startProcess()
            }else{
                //4-2
                //승인되지 않았다면 알림을 보여주고 앱 종료
                Toast.makeText(this, "외부 저장소 권한을 승인해야 합니다.", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        //4-2
        //*****저장소 권한 런처 실행...
        storagePermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)

        //5
        //음원데이터 관련 클래스 정의
        //app - java밑 패키지 우클릭 - Kotlin Class/File
        //Music.kt 클래스 생성

        //6
        //음원목록 화면 만들기
        //activity_main.xml 작성

    }//onCreate

    //3
    //권한 요청이 정상적으로 승인되면 호출하여 앱을 실행할 메서드 생성
    fun startProcess(){

        //9
        //지금까지 생성한 어댑터와 화면, 데이터 가져오는 메서드 연결
        //9-1
        //adapter 생성하고 어댑터 안에 정의해둔 musicList에 음원데이터를 전부 넘겨줌
        val adapter = MusicRecyclerAdapter()
        adapter.musicList.addAll(getMusicList())
        //9-2
        //데이터가 담긴 어댑터를 리사이클러뷰에 연결하고 레이아웃 매니저 설정
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

    }//startProcess

    //10
    //목록을 클릭해서 음원 실행하기
    //클릭 이벤트를 어댑터의 홀더에서 받아야되기 때문에
    //MusicRecyclerAdapter.kt에서 작성...

    //7
    //Music데이터에서 음원을 읽어오는 getMusicList()메서드 생성
    fun getMusicList(): List<Music>{

        //7-1
        //음원 정보의 주소를 listUrl변수에 저장
        val listUrl = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

        //7-2
        //음원 정보 테이블에서 읽어올 컬럼명을 배열로 정의
        //컬럼명은 이미 MediaStore에 상수로 정의되어 있음
        val proj = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.DURATION
        )

        //7-3
        //콘텐트 리졸버의 쿼리 메서드에
        //설정한 주소와 컬럼명을 담아 커서로 반환되는 데이터를
        //변수 curser에 저장
        val curser = contentResolver.query(listUrl, proj, null, null, null)

        //7-4
        //커서형태로 전달받아 꺼낸 데이터를 저장할 목록 변수 생성
        val musicList = mutableListOf<Music>()

        //7-5
        //반복문으로 커서를 이동하면서 데이터 한 줄씩 읽도록 while문 생성
        //읽은 데이터는 Music클래스에 옮긴 후
        //생성해뒀던 리스트 변수 musicList에 저장
        while (curser?.moveToNext() == true){
            //7-6
            //커서에서 데이터 꺼낼때 get()메서드 사용
            //(컬럼타입이 문자일 때: getString())
            //get메서드 사용 시 파라미터에 컬럼 데이터 순서를 숫자로입력함
            //커서는 데이터를 한 줄씩 꺼낼 때 순서없이 꺼내기 때문
            val id = curser.getString(0)
            val title = curser.getString(1)
            val artist = curser.getString(2)
            val albumId = curser.getString(3)
            val duration = curser.getLong(4)

            //7-7
            //읽은 데이터 Music클래스에 전달하고 리스트변수 musicList에 추가
            val music = Music(id, title, artist, albumId, duration)
            musicList.add(music)
        }

        //8
        //데이터가 다 담겨져있는 변수musicList를 호출한 측에 반환
        return musicList

        //9
        //fun startProcess()함수 작성

    }//getMusicList

}//MainActivity