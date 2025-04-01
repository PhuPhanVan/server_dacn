package PhanVanPhu.Java.DoAn.Repository;

import PhanVanPhu.Java.DoAn.Model.Transcription;
import PhanVanPhu.Java.DoAn.Model.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ITranscriptionRepository extends JpaRepository<Transcription, Long> {

}
