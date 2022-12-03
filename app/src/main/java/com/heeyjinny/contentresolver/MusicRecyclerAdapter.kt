package com.heeyjinny.contentresolver

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.heeyjinny.contentresolver.databinding.ItemRecyclerBinding
import java.text.SimpleDateFormat

//2
//리사이클러뷰의 어댑터 클래스 상속받는 MusicRecyclerAdapte클래스
//제네릭으로 만들어둔 Holder클래스 지정하고
//필수 메서드 3개 자동 생성
class MusicRecyclerAdapter: RecyclerView.Adapter<MusicRecyclerAdapter.Holder>() {

    //3
    //음악 목록을 저장해둘 변수 생성
    //제네릭으로 Music사용 컬렉션
    var musicList = mutableListOf<Music>()

    //5
    //화면에 보이는 아이템 레이아웃의 바인딩 생성 메서드 구현
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        //5-1
        //홀더의 바인딩 반환
        return Holder(binding)
    }

    //6
    //아이템 레이아웃에 데이터 출력 메서드 구현
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val music = musicList.get(position)
        //6-1
        //홀더의 setMusic메서드에 음악목록의 현재 위치 전달
        holder.setMusic(music)
    }

    //4
    //목록의 개수를 알려주는 메서드 리턴값 설정
    override fun getItemCount(): Int {
        return musicList.size
    }

    //1
    //이너 클래스로 홀더 클래스 생성
    //홀더 클래스는 항상 바인딩 1개를 파라미터로 가지고
    //상속받는 ViewHolder에 root로 넘겨줌
    inner class Holder(val binding: ItemRecyclerBinding): RecyclerView.ViewHolder(binding.root){

        //7-1
        //파라미터로 넘어온 music은 메서드가 실핼되는 순간에만 사용가능하기 때문에
        //클릭 시 음원이 플레이되는 것을 대비해
        //변수를 생성하여 현재 Music클래스가 가지고 있는 Uri저장하는 것이 좋음
        var musicUri: Uri? = null

        //7
        //setMusic()메서드 구현
        //onBindViewHolder에서 음악목록의 현재 위치 값을 받아
        //화면에 보여주기
        fun setMusic(music: Music){
            //7-2
            //run함수 사용하여 binding의 중복 최소화
            binding.run {

                //7-3
                //앨범 이미지뷰에 setImageURI()를 사용하여 이미지 세팅
                imageAlbum.setImageURI(music.getAlbumUri())
                //7-4
                //아티스트명, 음원명속성 값 입력
                textArtist.text = music.artist
                textTitle.text = music.title
                //7-5
                //음악 재생시간 SimpleDateFormat을 사용하여
                //원하는 형태 format하여 사용
                textDuration.text = SimpleDateFormat("mm:ss").format(music.duration)

            }

            //7-1
            //현재 Music클래스가 가지고 있는 Uri저장
            this.musicUri = music.getMusicUri()

        }//setMusic()

    }//Holder

}//MusicRecyclerAdapter