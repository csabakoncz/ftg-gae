package com.ck.ftg.client;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.drop.SimpleDropController;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Text;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Main implements EntryPoint {

	private static final String ID_TEXT = "text";
	private static final String ID_choicesPanel = "choicesPanel";
	private static final String ID_puzzleArea = "puzzleArea";
	private static final String ID_checkButton = "checkButton";
	
	private static final String ID_correctMsg = "correctMsg";
	private static final String ID_errorsMsg = "errorsMsg";
	private static final String ID_errorsAndUnfilledMsg = "errorsAndUnfilledMsg";
	private static final String ID_unfilledMsg = "unfilledMsg";
	
	
	private static final String GAP_ELEMENT = "em";

	private static final String ID = "id";

	private static final String NEWLINE = "\n";
	private static final String NBSP = "&nbsp;";
	private static final String PROP_PADDING_LEFT = "paddingLeft";

	private static final String STYLE_GAP_PANEL = "gapPanel";
	private static final String STYLE_CHOICE_WORD = "choiceWord";
	private static final String STYLE_GUESSED_WORD = "guessedWord";
	private static final String STYLE_CORRECT_WORD = "correctWord";
	private static final String STYLE_INCORRECT_WORD = "incorrectWord";
	
	private FlowPanel choicesPanel;
	private HTMLPanel puzzleArea;
	PickupDragController dragController;
	private Button checkResultsButton;

	public void onModuleLoad() {
		Element text = Document.get().getElementById(ID_TEXT);
		producePuzzle(text);
	}

	private void producePuzzle(Element text) {

		NodeList<Element> divs = text.getElementsByTagName(GAP_ELEMENT);

		dragController = new PickupDragController(RootPanel.get(), false);
		choicesPanel = new FlowPanel();
		RootPanel.get(ID_choicesPanel).add(choicesPanel);

		final List<String> entries = new ArrayList<String>();

		int length = divs.getLength();
		int longestText = 0;
		for (int i = 0; i < length; i++) {
			Element item = divs.getItem(i);

			String nodeValue = item.getInnerText();
			entries.add(nodeValue);

			String idVal = createId(i);
			item.setAttribute(ID, idVal);

			int nodeTextLen = nodeValue.length();

			if (nodeTextLen > longestText) {
				longestText = nodeTextLen;
			}

		}

		{
			// randomize the choices:
			List<String> randomEntries = new ArrayList<String>(entries);
			for (int index = 0; index < (length - 1); index++) {
				int newIndex = index + Random.nextInt(length - index);
				Collections.swap(randomEntries, index, newIndex);
			}
			for (String string : randomEntries) {
				createChoice(string);
			}
		}

		String textWithIds = text.getInnerHTML();
		// now remove the ids:
		for (int i = 0; i < length; i++) {
			Element item = divs.getItem(i);
			item.removeAttribute(ID);
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

		for (int i = 0; i < length; i++) {

			final FlowPanel l = createGapPanel(longestText);

			gaps.add(l);

			SimpleDropController sdc = createDropController(l);

			dragController.registerDropController(sdc);
			String idVal = createId(i);
			puzzleArea.addAndReplaceElement(l, idVal);
		}

		RootPanel.get(ID_puzzleArea).add(puzzleArea);

		Element checkButton = Document.get().getElementById(ID_checkButton);
		checkResultsButton = Button.wrap(checkButton);

		configureCheckHandler(entries, gaps);

	}

	private String createId(int i) {
		String idVal = "__id_" + i;
		return idVal;
	}

	private void configureCheckHandler(final List<String> entries,
			final List<FlowPanel> gaps) {
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
							widget.removeStyleName(STYLE_CORRECT_WORD);
							widget.addStyleName(STYLE_INCORRECT_WORD);
							errors++;
						}
					} else {
						unfilledCount++;
					}
				}

				String msg;
				
				if(errors==0 && unfilledCount==0){
					msg = getCorrectMsg();
				}else if(errors==0){
					msg=getUnfillesMsg(unfilledCount);
				}
				else if(unfilledCount==0){
					msg=getErrorsMsg(errors);
				}
				else{
					msg=getErrorsAndUnfilledMsg(errors, unfilledCount);
				}

				DialogBox dialogBox = new DialogBox(true, false);
				dialogBox.setText(msg);
				dialogBox.center();
			}

		});
	}

	private String getCorrectMsg() {
		String msg = RootPanel.get(ID_correctMsg).getElement().getInnerText();
		return msg;
	}

	private String getUnfillesMsg(int unfilledCount) {
		String msg = RootPanel.get(ID_unfilledMsg).getElement().getInnerText();
		msg = MessageFormat.format(msg, unfilledCount);
		return msg;
	}
	private String getErrorsMsg(int errorCount) {
		String msg = RootPanel.get(ID_errorsMsg).getElement().getInnerText();
		msg = MessageFormat.format(msg, errorCount);
		return msg;
	}
	private String getErrorsAndUnfilledMsg(int errorCount, int unfilledCount) {
		String msg = RootPanel.get(ID_errorsAndUnfilledMsg).getElement().getInnerText();
		msg = MessageFormat.format(msg, errorCount,unfilledCount);
		return msg;
	}

	private SimpleDropController createDropController(final FlowPanel l) {
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

					final Label il = createGuessedWord(sourceLabel.getText());
					l.add(il);
					il.getElement().getStyle().setTop(0, Unit.PX);
					dragController.makeDraggable(il);
				}
			}

			private Label createGuessedWord(String text) {
				final Label il = new InlineLabel(text);
				il.addStyleName(STYLE_CORRECT_WORD);
				il.addStyleName(STYLE_GUESSED_WORD);
				return il;
			};
		};
		return sdc;
	}

	private void createChoice(String nodeValue) {
		Label l = createChoiceLabel(nodeValue);
		choicesPanel.add(l);
		dragController.makeDraggable(l);
		{
			// append a newline so that the choices can wrap:
			Text newLine = com.google.gwt.dom.client.Document.get()
					.createTextNode(NEWLINE);
			choicesPanel.getElement().appendChild(newLine);
		}
	}

	private FlowPanel createGapPanel(int longestText) {
		final FlowPanel l = new FlowPanel();
		l.addStyleName(STYLE_GAP_PANEL);
		l.getElement().getStyle().setDisplay(Display.INLINE);
		l.getElement().getStyle().setPosition(Position.RELATIVE);

		// create an invisible element that ensures the gap size:
		InlineLabel invisibleLabel = new InlineLabel();
		invisibleLabel.getElement().setInnerHTML(NBSP);
		final String longestTextPadding = longestText + "em";
		invisibleLabel.getElement().getStyle()
				.setProperty(PROP_PADDING_LEFT, longestTextPadding);
		l.add(invisibleLabel);

		return l;
	}

	private Label createChoiceLabel(String nodeValue) {
		Label l = new InlineLabel(nodeValue);
		l.addStyleName(STYLE_CORRECT_WORD);
		l.addStyleName(STYLE_CHOICE_WORD);
		return l;
	}
}
