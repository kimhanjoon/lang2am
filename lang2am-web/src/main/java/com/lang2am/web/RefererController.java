package com.lang2am.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lang2am.domain.RefererVO;
import com.lang2am.service.RefererDAO;

@RestController
public class RefererController {

	@Autowired
	private RefererDAO refererDAO;

	@Value("${referer.scan.path}")
	String srcPath;

	@RequestMapping(value="/referer", method=RequestMethod.POST)
    public void regen() throws IOException {


		refererDAO.delete();

		visit(new File(srcPath));
    }

	private static Pattern PATTERN = Pattern.compile("L__.*?__[FUEL]?");

	private void visit(File file) throws IOException  {

		if( file == null ) {
			return;
		}

		if( file.isDirectory() ) {
			for (File subFile : file.listFiles()) {
				visit(subFile);
			}
		}

		else if( file.isFile() ) {

			try(BufferedReader br = new BufferedReader(new FileReader(file))) {

				String line = br.readLine();
				int lineNo = 1;
				while( line != null ) {
					Matcher m = PATTERN.matcher(line);
					while (m.find()) {
						dosomething(file, line, lineNo, m.group());
					}
					line = br.readLine();
					lineNo++;
				}
			}

		}
	}

	private void dosomething(File file, String line, int lineNo, String text) {

		refererDAO.insert(RefererVO.builder()
				.code(text)
				.refererType("SRC")
				.refererFullname(file.getAbsolutePath())
				.refererName(file.getName())
				.refererPosition("" + lineNo)
				.refererContent(line)
				.build()
		);
	}

}
