package m.project.test.SpeechAudio;

public interface VoiceRecorder {

    void launch();
    void close();
    void startRecord();
    void stopRecord();
    boolean isContinuous();
    boolean isRecording();
}
