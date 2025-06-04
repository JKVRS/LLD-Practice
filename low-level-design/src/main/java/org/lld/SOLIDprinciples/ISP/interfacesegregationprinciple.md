## Interface segregatio Principle

# Why?
* Have you ever implemented an interface… only to realize you had to write empty methods just to make the compiler happy?

Lest consider an example of MediaPlayer App supporting different types of media.
Audio file (MP3, WAV)
Video file (MP4, AVI)

- Let's say we have created a unified interface that handles everything.

```
interface MediaPlayer {
    void playAudio(String audioFile);
    void stopAudio();
    void adjustAdudioVolume(int volume);
    
    void playVideo(String videoFile);
    void stopVideo();
    void adjustVideoBrightness(int brightness);
    void displaySubtitles(String subtitileFile);
}
```

The problems arise when the app grows.

**Let's there is a requirement of pure audio-** a class should only handle sound.

```
class AudioPlayer implements MediaPlayer {
    @Override
    public void playAudio(String audioFile) {
        System.out.println("Playing audio file: " + audioFile);
    }

    @Override
    public void stopAudio() {
        System.out.println("Audio stopped.");
    }

    @Override
    public void adjustAudioVolume(int volume) {
        System.out.println("Audio volume set to: " + volume);
    }

    // 👎 Methods this class shouldn't care about:
    @Override
    public void playVideo(String videoFile) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public void stopVideo() { /* no-op */ }

    @Override
    public void adjustVideoBrightness(int brightness) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public void displaySubtitles(String subtitleFile) {
        throw new UnsupportedOperationException("Not supported.");
    }
}
```

## The problem is we are forcing the AudioPlayer to implements the sound realted behvior as well from the interface, and throwing unsupportedOperationException

* MediaPlayer is doing too much. it combines multiple unrelated responsibilities.
* Audio Playback
* Video Playback
* Subtitle handling
* Brightness control

suddenly we added new method to the interface, like enablePictureInPicture, it means all existing implementations - audio, video, must update.


This is tight coupling
**Clients should not be forced to depend on methods they do not use.**
**keep the interface highly focused**
**Increase cohesion**
**Define small Cohesive interface**

// To handle audio-only capabilities
```
interface AudioPlayerControls {
   void playAudio(String audioFile);
   void stopAudio();
   void adjustAudioVolume(int volume);
}
```
// To handle video-only capabilities
```
interface VideoPlayerControls {
   void playVideo(String videoFile);
   void stopVideo();
   void adjustVideoBrightness(int brightness);
   void displaySubtitles(String subtitleFile);
}
```

Class implements only the interfaces they need

```
class ModernAudioPlayer implements AudioPlayer {
    @Override
    public void playAudio(String audioFile) {
        System.out.println("ModernAudioPlayer: Playing audio - " + audioFile);
    }

    @Override
    public void stopAudio() {
        System.out.println("ModernAudioPlayer: Audio stopped.");
    }

    @Override
    public void adjustAudioVolume(int volume) {
        System.out.println("ModernAudioPlayer: Volume set to " + volume);
    }
}
```

```
class SilentVideoPlayer implements VideoPlayerControls {
    @Override
    public void playVideo(String videoFile) {
        System.out.println("SilentVideoPlayer: Playing video - " + videoFile);
    }

    @Override
    public void stopVideo() {
        System.out.println("SilentVideoPlayer: Video stopped.");
    }

    @Override
    public void adjustVideoBrightness(int brightness) {
        System.out.println("SilentVideoPlayer: Brightness set to " + brightness);
    }

    @Override
    public void displaySubtitles(String subtitleFile) {
        System.out.println("SilentVideoPlayer: Subtitles from " + subtitleFile);
    }
}
```

ComprehensiveMediaPlayer (audio+video)
```
class ComprehensiveMediaPlayer implements AudioPlayerControls, VideoPlayerControls {
    @Override
    public void playAudio(String audioFile) {
        System.out.println("ComprehensiveMediaPlayer: Playing audio - " + audioFile);
    }

    @Override
    public void stopAudio() {
        System.out.println("ComprehensiveMediaPlayer: Audio stopped.");
    }

    @Override
    public void adjustAudioVolume(int volume) {
        System.out.println("ComprehensiveMediaPlayer: Audio volume set to " + volume);
    }

    @Override
    public void playVideo(String videoFile) {
        System.out.println("ComprehensiveMediaPlayer: Playing video - " + videoFile);
    }

    @Override
    public void stopVideo() {
        System.out.println("ComprehensiveMediaPlayer: Video stopped.");
    }

    @Override
    public void adjustVideoBrightness(int brightness) {
        System.out.println("ComprehensiveMediaPlayer: Brightness set to " + brightness);
    }

    @Override
    public void displaySubtitles(String subtitleFile) {
        System.out.println("ComprehensiveMediaPlayer: Subtitles from " + subtitleFile);
    }
}


```


## Common Pitfalls 
1. over-segregation (always try to group related methods which are logically similar or capabilities)
2. Designed the interface as per client perspective
3. Lack of Cohesion (Creating interfaces that aren't tightly related)

