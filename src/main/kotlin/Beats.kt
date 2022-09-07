import java.io.File
import javax.sound.sampled.AudioSystem
import kotlinx.coroutines.*

// coroutines let you write code that's run asynchronously

// gradle is a build tool that lets you compile and deploy code, and include any
// third-party libraries that your code needs

suspend fun playBeats(beats: String, file: String){ // when you call a suspendable function (such a delay) from another function
    // that function must be marked with suspend
    val parts = beats.split ("x")
    var count = 0
    for (x in parts){
        count += x.length + 1
        if (x == ""){
            playSound(file)
        } else{
            //Thread.sleep (100 * (x.length + 1L)) //pauses current thread
            delay (100 * (x.length + 1L)) //pauses current coroutine
            if (count < beats.length){
                playSound(file)
            }
        }
    }
}

fun playSound(file: String){
    val clip = AudioSystem.getClip()
    val audioInputStream = AudioSystem.getAudioInputStream(
        File(
            file
        )
    )
    clip.open(audioInputStream)
    clip.start()
}

suspend fun main() {
    /*GlobalScope.launch { playBeats("x-x-x-x-x-x", "toms.aiff") }
    playBeats("x-----x-----", "crash_cymbal.aiff")*/
    // behind the scenes this creates a new thread which the coroutine runs in

    // launching a coroutine is like starting a separate thread of execution, or thread
    // it's more efficient to use coroutines in your code that it is to use threads
    // CPU can usually run a limited number of threads at the same time
    // however, coroutines run on a shared pool of threads by default
    // in this particular code GlobalScope.launch was used to run a new coroutine in the background
    // this created a new thread which the coroutine runs in, so both sounds were played in separate threads

    // if you want your code to run in the same thread but in separate coroutines, you can use the runBlocking fun

    runBlocking {

    launch { playBeats("x-x-x-x-x-x", "toms.aiff") }
    playBeats("x-----x-----", "crash_cymbal.aiff")
    // to run separate coroutines in the same thread
    }

    // one thread can run many coroutines
    // use the launch fun to launch a new coroutine
    // use the runBlocking fun to block the current thread until the code it contains has finished running
    // the delay fun suspends the code for a specified length of time



}

