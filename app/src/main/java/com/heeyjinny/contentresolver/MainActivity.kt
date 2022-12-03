package com.heeyjinny.contentresolver

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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
        storagePermission = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            isGranted ->
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

    }//onCreate

    //3
    //권한 요청이 정상적으로 승인되면 호출하여 앱을 실행할 메서드 생성
    fun startProcess(){

    }//startProcess

}//MainActivity