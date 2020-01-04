JCC=javac

run:
	$(JCC) src/Servidor/*.java

clean:
	rm src/Servidor/*.class
	rm MusicFiles/*.mp3
	rm src/DownloadedMusic/*.mp3

