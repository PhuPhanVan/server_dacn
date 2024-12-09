package TranQuocToan.Java.DoAn.Repository;

import TranQuocToan.Java.DoAn.Model.Transcription;
import TranQuocToan.Java.DoAn.Model.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ITranscriptionRepository extends JpaRepository<Transcription, Long> {

}
