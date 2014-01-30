package com.ck.ftg.client;

import java.util.ArrayList;
import java.util.List;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.drop.SimpleDropController;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Text;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Main implements EntryPoint {
	private FlowPanel choicesPanel;
	private HTMLPanel puzzleArea;
	PickupDragController dragController;
	private Button checkResultsButton;

	public void onModuleLoad() {
		String text = "Alma a <e>fa</e> alatt, nyári  <e>piros alma</e>. "
				+ "Engem szid, meg minden. ";
		text = text + text;
		text = text + text;

		String parameter = com.google.gwt.user.client.Window.Location
				.getParameter("test");
		if (parameter == null) {
			producePuzzle(text);
		} else {
			final TextArea ta = new TextArea();
			RootPanel.get().add(ta);

			ta.setText(text);
			Button b = new Button("Process");
			RootPanel.get().add(b);

			b.addClickHandler(new ClickHandler() {

				public void onClick(ClickEvent event) {
					producePuzzle(ta.getText());
				}

			});
		}
	}

	private void producePuzzle(String text) {
		String xmlText = "<d>" + text + "</d>";
		Document puzzleDoc = XMLParser.parse(xmlText);

		NodeList divs = puzzleDoc.getElementsByTagName("e");
		int length = divs.getLength();

		//create id attributes
		for(int i=0; i<length; i++){
			Node item = divs.item(i);
			if(item instanceof Element){
				((Element)item).setAttribute("id", "id1");
			}
		}
		
		String textWithIds = puzzleDoc.getDocumentElement().toString();
		//strip the starting <d> and ending </d>:
		int textLen=textWithIds.length();
		textWithIds=textWithIds.substring(3,textLen-4);

		if (choicesPanel != null) {
			choicesPanel.removeFromParent();
			puzzleArea.removeFromParent();
			checkResultsButton.removeFromParent();
		}

		dragController = new PickupDragController(RootPanel.get(), false);
		choicesPanel = new FlowPanel();
		choicesPanel.addStyleName("choicesPanel");

		RootPanel.get().add(choicesPanel);

		final List<String> entries = new ArrayList<String>();
		List<String> ids = new ArrayList<String>();

		int longestText = 0;

		for (int i = 0; i < length; i++) {
			Node item = divs.item(i);
			String nodeValue = item.getFirstChild().getNodeValue();

			int nodeTextLen = nodeValue.length();

			if (nodeTextLen > longestText) {
				longestText = nodeTextLen;
			}

			String id = ((Element) item).getAttribute("id");

			createChoice(nodeValue);

			entries.add(nodeValue);
			ids.add(id);

		}

		// make it possible to drag words back from the text:
		SimpleDropController choicesPanelDropController = new SimpleDropController(
				choicesPanel) {
			public void onDrop(DragContext context) {
				if (context.draggable instanceof Label) {
					Label sourceLabel = (Label) context.draggable;
					createChoice(sourceLabel.getText());
				}
			}
		};
		dragController.registerDropController(choicesPanelDropController);

		puzzleArea = new HTMLPanel(textWithIds);
		final List<FlowPanel> gaps = new ArrayList<FlowPanel>();
		for (String id : ids) {

			final FlowPanel l = createGapPanel(longestText);

			gaps.add(l);

			SimpleDropController sdc = new SimpleDropController(l) {
				public void onDrop(DragContext context) {
					if (context.draggable instanceof Label) {

						Label sourceLabel = (Label) context.draggable;

						// check if we already have a label:
						int widgetCount = l.getWidgetCount();

						if (widgetCount == 2) {

							Widget widget = l.getWidget(1);
							// we must remove it and its content put back to the
							// choices panel:
							Label existingLabel = (Label) widget;
							createChoice(existingLabel.getText());
							existingLabel.removeFromParent();
						}

						final Label il = createGuessedWord(sourceLabel
								.getText());
						l.add(il);
						il.getElement().getStyle().setTop(0, Unit.PX);
						dragController.makeDraggable(il);
					}
				}

				private Label createGuessedWord(String text) {
					final Label il = new InlineLabel(text);
					il.addStyleName("correctWord");
					il.addStyleName("guessedWord");
					return il;
				};
			};

			dragController.registerDropController(sdc);
			puzzleArea.addAndReplaceElement(l, id);
		}

		puzzleArea.addStyleName("puzzleArea");
		RootPanel.get().add(puzzleArea);

		checkResultsButton = new Button("Check Result");
		RootPanel.get().add(checkResultsButton);
		checkResultsButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				int gapcount = gaps.size();
				int unfilledCount = 0;
				int errors = 0;

				for (int i = 0; i < gapcount; i++) {

					FlowPanel gapPanel = gaps.get(i);
					int widgetCount = gapPanel.getWidgetCount();
					if (widgetCount == 2) {
						Widget widget = gapPanel.getWidget(1);
						String text = ((Label) widget).getText();
						String expectedString = entries.get(i);
						if (false == expectedString.equals(text)) {
							widget.removeStyleName("correctWord");
							widget.addStyleName("incorrectWord");
							errors++;
						}
					} else {
						unfilledCount++;
					}
				}

				String msg = "Correct!";
				if (errors > 00 || unfilledCount > 0) {
					msg = errors + " errors, " + unfilledCount + " unfilled";
				}
				DialogBox dialogBox = new DialogBox(true, false);
				dialogBox.setText(msg);
				dialogBox.center();
			}
		});

	}

	private void createChoice(String nodeValue) {
		Label l = createChoiceLabel(nodeValue);
		choicesPanel.add(l);
		dragController.makeDraggable(l);
		{
			//append a newline so that the choices can wrap:
			Text newLine = com.google.gwt.dom.client.Document.get()
					.createTextNode("\n");
			choicesPanel.getElement().appendChild(newLine);
		}
	}

	private FlowPanel createGapPanel(int longestText) {
		final FlowPanel l = new FlowPanel();
		l.addStyleName("gapPanel");
		l.getElement().getStyle().setDisplay(Display.INLINE);
		l.getElement().getStyle().setPosition(Position.RELATIVE);

		// create an invisible element that ensures the gap size:
		InlineLabel invisibleLabel = new InlineLabel();
		invisibleLabel.getElement().setInnerHTML("&nbsp;");
		final String longestTextPadding = longestText + "em";
		invisibleLabel.getElement().getStyle()
				.setProperty("paddingLeft", longestTextPadding);
		l.add(invisibleLabel);

		return l;
	}

	private Label createChoiceLabel(String nodeValue) {
		Label l = new InlineLabel(nodeValue);
		l.addStyleName("correctWord");
		l.addStyleName("choiceWord");
		return l;
	}
}
