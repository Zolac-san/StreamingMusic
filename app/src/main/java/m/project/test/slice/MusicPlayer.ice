module MusicPlayer {
    interface Player {
        string play(string title,string author,string album);
        void stop(string url);
    };
};