import android.media.MediaRecorder

class AudioRecorder {

    private var mediaRecorder: MediaRecorder? = null
    var isRecording = false
        private set

    fun startRecording(filePath: String) {
        if (isRecording) return

        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            setOutputFile(filePath)
            prepare()
            start()
        }
        isRecording = true
    }

    fun stopRecording() {
        if (!isRecording) return

        mediaRecorder?.apply {
            stop()
            release()
        }
        mediaRecorder = null
        isRecording = false
    }
}
