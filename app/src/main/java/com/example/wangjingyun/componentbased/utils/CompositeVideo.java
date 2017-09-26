package com.example.wangjingyun.componentbased.utils;

import android.content.Context;
import android.widget.Toast;

import com.coremedia.iso.boxes.Container;
import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.Track;
import com.googlecode.mp4parser.authoring.builder.DefaultMp4Builder;
import com.googlecode.mp4parser.authoring.container.mp4.MovieCreator;
import com.googlecode.mp4parser.authoring.tracks.AppendTrack;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 合成视频
 * Created by Administrator on 2017/9/26.
 */

public class CompositeVideo {


    private Context context;
    private String[] videoUris;
    private String output;
    public CompositeVideo(Context context,String[] videoUris,String output){
        this.context=context;
        this.videoUris=videoUris;
        this.output=output;
    }
    public void videoSplice(){
        //下面是github上mp4parser源码,就可以拼接视频也可以拼接声音
        try {
            List<Movie> inMovies = new ArrayList<Movie>();
            for (String videoUri : videoUris) {
                inMovies.add(MovieCreator.build(videoUri));
            }

            List<Track> videoTracks = new LinkedList<Track>();
            List<Track> audioTracks = new LinkedList<Track>();

            for (Movie m : inMovies) {
                for (Track t : m.getTracks()) {
                    if (t.getHandler().equals("soun")) {
                        audioTracks.add(t);
                    }
                    if (t.getHandler().equals("vide")) {
                        videoTracks.add(t);
                    }
                }
            }

            Movie result = new Movie();

            if (!audioTracks.isEmpty()) {
                result.addTrack(new AppendTrack(audioTracks.toArray(new Track[audioTracks.size()])));
            }
            if (!videoTracks.isEmpty()) {
                result.addTrack(new AppendTrack(videoTracks.toArray(new Track[videoTracks.size()])));
            }

            Container out = new DefaultMp4Builder().build(result);

            FileChannel fc = new RandomAccessFile(output, "rw").getChannel();
            out.writeContainer(fc);
            fc.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

}
