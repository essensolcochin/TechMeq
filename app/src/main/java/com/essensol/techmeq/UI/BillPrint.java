package com.essensol.techmeq.UI;

import android.content.Context;
import android.os.Build;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.essensol.techmeq.R;

public class BillPrint extends AppCompatActivity {


	WebView myWebView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_print);




		myWebView = findViewById(R.id.bill);

		//add webview client to handle event of loading
		myWebView.setWebViewClient(new WebViewClient() {

			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				return false;
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				//if page loaded successfully then show print button

			}
		});




		       String htmlDocument = "<html>\n" +
					   "\t<head>\n" +
					   "\t\t<meta charset=\"utf-8\">\n" +
					   "\t\t<title>Invoice</title>\n" +
					   "\t\t<link rel=\"stylesheet\" href=\"style.css\">\n" +
					   "\t\t<link rel=\"license\" href=\"https://www.opensource.org/licenses/mit-license/\">\n" +
					   "\t\t<script src=\"script.js\"></script>\n" +
					   "\t</head>\n" +
					   "\t<body>\n" +
					   "\t\t<header>\n" +
					   "\t\t\t<h1>Invoice</h1>\n" +
					   "\t\t\t<address contenteditable>\n" +
					   "    <p>BHAGYA ENCLAVE APPARTMENT</p>\n" +
					   "    <p>SAI NARAYANA COLONY ROAD NO.2<br>JAIPURI COLONY</p>\n" +
					   "\t\t\t\t<p>1234567890</p>\n" +
					   "\t\t\t</address>\n" +

					   "\t\t</header>\n" +
					   "\t\t<article>\n" +
					   "\t\t\t<h1>Recipient</h1>\n" +
					   "\t\t\t<address contenteditable>\n" +
					   "\t\t\t\t<p>rahul<br>307</p>\n" +
					   "\t\t\t</address>\n" +
					   "\t\t\t<table class=\"meta\">\n" +
					   "\t\t\t\t<tr>\n" +
					   "\t\t\t\t\t<th><span contenteditable>Invoice #</span></th>\n" +
					   "\t\t\t\t\t<td><span contenteditable></span></td>\n" +
					   "\t\t\t\t</tr>\n" +
					   "\t\t\t\t<tr>\n" +
					   "\t\t\t\t\t<th><span contenteditable>Date</span></th>\n" +
					   "\t\t\t\t\t<td><span contenteditable></span></td>\n" +
					   "\t\t\t\t</tr>\n" +
					   "\t\t\t\t<tr>\n" +
					   "\t\t\t\t\t<th><span contenteditable>Amount Due</span></th>\n" +
					   "\t\t\t\t\t<td><span id=\"prefix\" contenteditable></span><span></span></td>\n" +
					   "\t\t\t\t</tr>\n" +
					   "\t\t\t</table>\n" +
					   "\t\t\t<table class=\"inventory\">\n" +
					   "\t\t\t\t<thead>\n" +
					   "\n" +
					   "\t\t\t\t\t\t<th><span contenteditable>Maintainance/Others</span></th>\n" +
					   "          <th><span contenteditable>Payment Mode</span></th>\n" +
					   "\n" +
					   "\n" +
					   "          <th><span      contenteditable>Status</span></th>\n" +
					   "\n" +
					   "\t\t\t\t\t</tr>\n" +
					   "\t\t\t\t</thead>\n" +
					   "\t\t\t\t<tbody>\n" +
					   "\t\t\t\t\t<tr>\n" +
					   "\t\t\t\t\t\t<td><a class=\"cut\">-</a><span contenteditable></span></td>\n" +
					   "\t\t\t\t\t\t<td><contenteditable></></td>\n" +
					   "\t\t\t\t\t\t<td><span data-prefix></span><span contenteditable></></td>\n" +
					   "              <contenteditable></></t\n" +
					   "            <td><span data-prefix></span><span></span></td>\n" +
					   "\t\t\t\t\t</tr>\n" +
					   "\t\t\t\t</tbody>\n" +
					   "\t\t\t</table>\n" +
					   "\t\t\t<a class=\"add\">+</a>\n" +
					   "\t\t\t<table class=\"balance\">\n" +
					   "\t\t\t\t<tr>\n" +
					   "\t\t\t\t\t<th><span contenteditable>Total</span></th>\n" +
					   "\t\t\t\t\t<td><span data-prefix></span>\n" +
					   "          <span contenteditable></span></td>\n" +
					   "\t\t\t\t</tr>\n" +
					   "\t\t\t\t<tr>\n" +
					   "\t\t\t\t\t<th><contenteditable>Amount Paid</span></th>\n" +
					   "          <td><span data-prefix></span><span contenteditable></span></td>\n" +
					   "\t\t\t\t</tr>\n" +
					   "\t\t\t\t<tr>\n" +
					   "\n" +
					   "\t\t\t\t\t<span data-prefix></span><span></span>\n" +
					   "\t\t\t\t</tr>\n" +
					   "\t\t\t</table>\n" +
					   "\t\t</article>\n" +
					   "\t\t<aside>\n" +
					   "\t\t\t<h1><span contenteditable>Additional Notes</span></h1>\n" +
					   "\t\t\t<div contenteditable>\n" +
					   "    <p>A finance charge/panelty will be made on unpaid balances after 30 days.</p>\n" +
					   "\t\t\t</div>\n" +
					   "\t\t</aside>\n" +
					   "\t</body>\n" +
					   "</html>";

		myWebView.loadData(htmlDocument, "text/HTML", "UTF-8");

    }


	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	private void createWebPrintJob(WebView webView) {

		//create object of print manager in your device
		PrintManager printManager = (PrintManager) this.getSystemService(Context.PRINT_SERVICE);

		//create object of print adapter
		PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter();

		//provide name to your newly generated pdf file
		String jobName = getString(R.string.app_name) + " Print Test";

		//open print dialog
		printManager.print(jobName, printAdapter, new PrintAttributes.Builder().build());
	}

	//perform click pdf creation operation on click of print button click

	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	public void printPDF(View view) {
		createWebPrintJob(myWebView);
	}



/*
*/

}
