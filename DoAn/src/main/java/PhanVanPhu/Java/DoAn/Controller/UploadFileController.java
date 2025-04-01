package PhanVanPhu.Java.DoAn.Controller;

import PhanVanPhu.Java.DoAn.Model.Question;
import PhanVanPhu.Java.DoAn.Model.Vocabulary;
import PhanVanPhu.Java.DoAn.Repository.IQuestionRepository;
import PhanVanPhu.Java.DoAn.Repository.VocabularyRepository;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;

@RestController
@RequestMapping("/api")
public class UploadFileController {

    @Autowired
    private IQuestionRepository questionRepository;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file,
                             @RequestParam("subject") String subject, // Thêm subject từ request
                             @RequestParam("questionType") String questionType // Thêm questionType từ request


    ) {
        if (file.isEmpty()) {
            return "No file uploaded!";
        }

        try (InputStream inputStream = file.getInputStream()) {
            XWPFDocument doc = new XWPFDocument(inputStream);

            // Đọc bảng để lấy đáp án đúng
            Map<Integer, String> correctAnswersMap = parseAnswerTable(doc);

            // Gọi parseQuestionsAndChoices và truyền thêm subject, questionType
            parseQuestionsAndChoices(doc, correctAnswersMap, subject, questionType);

            return "File uploaded and processed successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error processing file: " + e.getMessage();
        }
    }

    private Map<Integer, String> parseAnswerTable(XWPFDocument doc) {
        Map<Integer, String> correctAnswersMap = new HashMap<>();
        List<XWPFTable> tables = doc.getTables();

        if (!tables.isEmpty()) {
            XWPFTable answerTable = tables.get(0); // Giả sử bảng đầu tiên chứa đáp án
            List<XWPFTableRow> rows = answerTable.getRows();

            for (int i = 0; i < rows.size(); i++) {
                XWPFTableRow row = rows.get(i);
                List<String> rowValues = new ArrayList<>();

                // Duyệt qua từng ô (cell) trong hàng
                row.getTableCells().forEach(cell -> rowValues.add(cell.getText().trim()));

                // Xử lý hàng chứa số thứ tự câu hỏi và đáp án
                if (i % 2 == 0 && i + 1 < rows.size()) { // Hàng chẵn chứa số câu hỏi, hàng lẻ chứa đáp án
                    List<String> nextRowValues = new ArrayList<>();
                    rows.get(i + 1).getTableCells().forEach(cell -> nextRowValues.add(cell.getText().trim()));

                    // Ghép số thứ tự câu hỏi và đáp án tương ứng
                    for (int j = 0; j < rowValues.size(); j++) {
                        try {
                            Integer questionNumber = Integer.parseInt(rowValues.get(j));
                            String correctAnswer = nextRowValues.get(j);
                            correctAnswersMap.put(questionNumber, correctAnswer);
                        } catch (NumberFormatException e) {
                            System.err.println("Invalid question number format: " + rowValues.get(j));
                        } catch (IndexOutOfBoundsException e) {
                            System.err.println("Mismatched question and answer columns at index: " + j);
                        }
                    }
                }
            }
        }

        return correctAnswersMap;
    }


    private void parseQuestionsAndChoices(XWPFDocument doc, Map<Integer, String> correctAnswersMap,
                                          String subject,
                                          String questionType
    ) {
        List<String> currentChoices = new ArrayList<>();
        Question currentQuestion = null;

        for (XWPFParagraph paragraph : doc.getParagraphs()) {
            String text = paragraph.getText().trim();

            if (text.matches("^\\d+\\..*")) { // Nếu là câu hỏi
                // Lưu câu hỏi trước đó
                if (currentQuestion != null) {
                    saveQuestion(currentQuestion, currentChoices, correctAnswersMap);
                }

                // Tạo câu hỏi mới
                String questionText = text.substring(text.indexOf('.') + 1).trim();
                Integer questionNumber = Integer.parseInt(text.split("\\.")[0].trim());

                currentQuestion = new Question();
                currentQuestion.setQuestionText(questionText);
                currentQuestion.setSubject(subject); // Lưu subject từ frontend
                currentQuestion.setQuestionType(questionType); // Lưu questionType từ frontend

                currentChoices.clear();

            } else if (text.matches("^[A-D]\\..*")) { // Nếu là lựa chọn
                currentChoices.add(text.trim());
            }
        }

        // Lưu câu hỏi cuối cùng
        if (currentQuestion != null) {
            saveQuestion(currentQuestion, currentChoices, correctAnswersMap);
        }
    }

    private void saveQuestion(Question question, List<String> choices, Map<Integer, String> correctAnswersMap) {
        Optional<Question> existingQuestion = questionRepository.findByQuestionTextIgnoreCase(question.getQuestionText());
        if (existingQuestion.isPresent()) {
            System.out.println("Duplicate question found, skipping: " + question.getQuestionText());
            return;
        }

        question.setChoices(new ArrayList<>(choices));

        // Lưu vào repository trước để lấy id
        question = questionRepository.save(question);

        Long id = question.getId(); // Lấy id sau khi lưu
        if (id != null && correctAnswersMap.containsKey(id.intValue())) {
            List<String> correctAnswers = new ArrayList<>();
            correctAnswers.add(correctAnswersMap.get(id.intValue()));
            question.setCorrectAnswers(correctAnswers);
            questionRepository.save(question); // Cập nhật lại câu hỏi
        }
    }
    //////////
//
    @Autowired
    private VocabularyRepository vocabularyRepository;

    @PostMapping("/uploadVocabulary")
    public String uploadVocabularyFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "No file uploaded!";
        }

        try (InputStream inputStream = file.getInputStream()) {
            XWPFDocument doc = new XWPFDocument(inputStream);

            // Đọc từng từ từ file và xử lý
            List<String> words = extractWordsFromDocument(doc);
            for (String word : words) {
                processWord(word);
            }

            return "Vocabulary processed successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error processing file: " + e.getMessage();
        }
    }

    private List<String> extractWordsFromDocument(XWPFDocument doc) {
        List<String> words = new ArrayList<>();
        for (XWPFParagraph paragraph : doc.getParagraphs()) {
            String[] tokens = paragraph.getText().trim().split("\\s+");
            for (String token : tokens) {
                String cleanedWord = token.replaceAll("[^a-zA-Z]", "").toLowerCase();
                if (!cleanedWord.isEmpty()) {
                    words.add(cleanedWord);
                }
            }
        }
        return words;
    }

    private void processWord(String word) {
        // Kiểm tra từ đã tồn tại trong cơ sở dữ liệu chưa
        if (vocabularyRepository.findByWordIgnoreCase(word).isPresent()) {
            System.out.println("Word already exists in the database: " + word);
            return;
        }

        // Gọi API từ điển
        try {
            Vocabulary vocabulary = fetchVocabularyData(word);
            vocabularyRepository.save(vocabulary);
            System.out.println("Saved word: " + word);
        } catch (Exception e) {
            System.err.println("Error fetching data for word: " + word + ", Error: " + e.getMessage());
        }
    }

    private Vocabulary fetchVocabularyData(String word) throws Exception {
        String apiUrl = "https://api.dictionaryapi.dev/api/v2/entries/en/" + word;
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object>[] response = restTemplate.getForObject(apiUrl, Map[].class);
        if (response == null || response.length == 0) {
            throw new Exception("No data found for word: " + word);
        }

        Map<String, Object> wordData = response[0];
        Vocabulary vocabulary = new Vocabulary();
        vocabulary.setWord((String) wordData.get("word"));

        // Extract audio
        List<Map<String, String>> phonetics = (List<Map<String, String>>) wordData.get("phonetics");
        if (phonetics != null) {
            for (Map<String, String> phonetic : phonetics) {
                if (phonetic.get("audio") != null && !phonetic.get("audio").isEmpty()) {
                    vocabulary.setAudio(phonetic.get("audio"));
                    break;
                }
            }
        }

        // Extract meanings
        List<Map<String, Object>> meanings = (List<Map<String, Object>>) wordData.get("meanings");
        if (meanings != null && !meanings.isEmpty()) {
            Map<String, Object> meaning = meanings.get(0); // Take the first meaning
            vocabulary.setPartOfspeech((String) meaning.get("partOfSpeech"));

            List<Map<String, Object>> definitions = (List<Map<String, Object>>) meaning.get("definitions");
            if (definitions != null && !definitions.isEmpty()) {
                Map<String, Object> definition = definitions.get(0); // Take the first definition
                vocabulary.setDefinition((String) definition.get("definition"));
                vocabulary.setExample((String) definition.get("example"));
            }
        }

        return vocabulary;
    }
}