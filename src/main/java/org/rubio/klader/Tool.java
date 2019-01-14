package org.rubio.klader;

import org.apache.commons.cli.*;
import org.rubio.klader.core.console.ConsoleApplication;

import java.io.IOException;

/**
 * Tool kladr base from http://www.gnivc.ru/inf_provision/classifiers_reference/kladr/
 *
 * Download the latest archive  from http://www.gnivc.ru/html/gnivcsoft/KLADR/zerkalo/kladr.zip
 */
public class Tool {

    public static void main(String [] args) throws IOException, ParseException, ClassNotFoundException {
        ConsoleApplication application = new ConsoleApplication(System.out);
        application.run(args);
    }
}
