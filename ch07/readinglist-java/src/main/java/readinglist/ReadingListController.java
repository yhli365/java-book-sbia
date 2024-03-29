package readinglist;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 使用注入的CounterService和GaugeService.
 * 
 * @author yhli
 *
 */
@Controller
@RequestMapping("/")
public class ReadingListController {

	private ReadingListRepository readingListRepository;
	private AmazonProperties amazonConfig;
	private CounterService counterService;
	private GaugeService gaugeService;

	@Autowired
	public ReadingListController(ReadingListRepository readingListRepository, AmazonProperties amazonConfig,
			CounterService counterService, GaugeService gaugeService) {
		this.readingListRepository = readingListRepository;
		this.amazonConfig = amazonConfig;
		this.counterService = counterService;
		this.gaugeService = gaugeService;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/fail")
	public void fail() {
		throw new RuntimeException();
	}

	@ExceptionHandler(value = RuntimeException.class)
	@ResponseStatus(value = HttpStatus.BANDWIDTH_LIMIT_EXCEEDED)
	public String error() {
		return "error";
	}

	@RequestMapping(method = RequestMethod.GET)
	public String readersBooks(Reader reader, Model model) {
		List<Book> readingList = readingListRepository.findByReader(reader);
		if (readingList != null) {
			model.addAttribute("books", readingList);
			model.addAttribute("reader", reader);
			model.addAttribute("amazonID", amazonConfig.getAssociateId());
		}
		return "readingList";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String addToReadingList(Reader reader, Book book) {
		book.setReader(reader);
		readingListRepository.save(book);
		counterService.increment("books.saved");
		gaugeService.submit("books.save.time", System.currentTimeMillis());
		return "redirect:/";
	}

}
