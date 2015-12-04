package site.eris.sample;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import site.eris.test.domain.Book;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by wulinjie on 2015/12/3.
 */
@Controller
@RequestMapping("/sample-jsonp")
public class JsonpController {

	/**
	 * 测试同源策略
	 * @return
	 */
	@RequestMapping("")
	public String index(){
		return "sample-jsonp";
	}

	@RequestMapping(value = "/book1")
	@ResponseBody
	public Book book1(HttpServletRequest request){
		Book book = new Book();
		book.setBookName("Ramcharitmanas");
		book.setWriter("TulasiDas");
		return book;
	}

	@RequestMapping(value = "/book2")
	@ResponseBody
	public ResponseEntity<Book> book2(HttpServletRequest request){
		Book book = new Book();
		book.setBookName("Ramayan");
		book.setWriter("Valmiki");
		return ResponseEntity.accepted().body(book);
	}

}
