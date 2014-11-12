package tpbScraper.domain

public enum MediaType {

    AUDIO("100"),
    AUDIO_MUSIC("101"),
    AUDIO_AUDIOBOOKS("102"),
    AUDIO_SOUNDCLIPS("103"),
    AUDIO_FLAC("104"),
    AUDIO_OTHER("199"),

    GAMES("400"),
    GAMES_PC("401"),
    GAMES_MAC("402"),
    GAMES_PSX("403"),
    GAMES_XBOX360("404"),
    GAMES_WII("405"),
    GAMES_HANDHELD("406"),
    GAMES_IOS("407"),
    GAMES_ANDRIOD("408"),
    GAMES_OTHER("499"),

    VIDEO("200"),
    VIDEO_MOVIES("201"),
    VIDEO_MOVIES_DVDR("202"),
    VIDEO_MOVIES_HD("207"),
    VIDEO_TVSHOWS("205"),
    VIDEO_TVSHOWS_HD("208"),
    VIDEO_MUSICVIDEOS("203"),
    VIDEO_MOVIECLIPS("204"),
    VIDEO_HANDHELD("206"),
    VIDEO_3D("209"),
    VIDEO_OTHER("299"),

    PORN("500"),
    PORN_MOVIES("501"),
    PORN_MOVIES_DVDR("502"),
    PORN_MOVIES_HD("505"),
    PORN_PICTURES("503"),
    PORN_GAMES("504"),
    PORN_MOVIECLIPS("506"),
    PORN_OTHER("599"),

    APPLICATIONS("300"),
    APPLICATIONS_WINDOWS("301"),
    APPLICATIONS_MAC("302"),
    APPLICATIONS_UNIX("303"),
    APPLICATIONS_HANDHELD("304"),
    APPLICATIONS_IOS("305"),
    APPLICATIONS_ANDRIOD("306"),
    APPLICATIONS_OTHER("399"),

    OTHER("600"),
    OTHER_EBOOKS("601"),
    OTHER_COMICS("602"),
    OTHER_PICTURES("603"),
    OTHER_COVERS("604"),
    OTHER_PHYSIBLES("605"),
    OTHER_OTHER("699")

    private String urlCode

    MediaType(String urlCode) {
        this.urlCode = urlCode
    }

    public String getUrlCode(){
        return urlCode
    }

    static MediaType fromString(String mediaTypeName){
        values().find { it.toString() == mediaTypeName }
    }
}