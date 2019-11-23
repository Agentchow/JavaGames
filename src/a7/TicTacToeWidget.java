package a7;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TicTacToeWidget extends JPanel implements ActionListener, SpotListener{
	
	private enum Player {WHITE, BLACK};
	
		private TicTacJSpotBoard _board;		/* SpotBoard playing area. */
		private JLabel _message;		/* Label for messages. */
		private boolean _game_won;		/* Indicates if games was been won already.*/

		private Player _next_to_play;	/* Identifies who has next turn. */

		
	public TicTacToeWidget() {
			
		/* Create SpotBoard and message label. */
			
		_board = new TicTacJSpotBoard(3,3);
		_message = new JLabel();
			
		/* Set layout and place SpotBoard at center. */
			
		setLayout(new BorderLayout());
		add(_board, BorderLayout.CENTER);
	
		/* Create subpanel for message area and reset button. */
			
		JPanel reset_message_panel = new JPanel();
		reset_message_panel.setLayout(new BorderLayout());
			/* Reset button. Add ourselves as the action listener. */
			
		JButton reset_button = new JButton("Restart");
		reset_button.addActionListener(this);
		reset_message_panel.add(reset_button, BorderLayout.EAST);
		reset_message_panel.add(_message, BorderLayout.CENTER);

		/* Add subpanel in south area of layout. */
		
		add(reset_message_panel, BorderLayout.SOUTH);

		/* Add ourselves as a spot listener for all of the
		 * spots on the spot board.
		 */
		_board.addSpotListener(this);

		/* Reset game. */
		resetGame();
		}
	
	private void resetGame() {
		
		for (Spot s : _board) {
		s.clearSpot();
        s.setSpotColor(new Color(0.8f, 0.8f, 0.8f));
		}
		
		_game_won = false;
		_next_to_play = Player.WHITE;
	
		_message.setText("Welcome to the TicTacToe. WHITE to play");
		
	}

	public void spotClicked(Spot s) {
		if (_game_won) {
			return;
		}
		
		String player_name = null;
		String next_player_name = null;
		Color player_color = null;
		
		if (_next_to_play == Player.WHITE) {
			player_color = Color.WHITE;
			player_name = "WHITE";
			next_player_name = "BLACK";
			_next_to_play = Player.BLACK;
		} else {
			player_color = Color.BLACK;
			player_name = "BLACK";
			next_player_name = "WHITE";
			_next_to_play = Player.WHITE;			
		}

		if (s.isEmpty()) {
			s.setSpotColor(player_color);
			s.toggleSpot();
		} else {
			return;
		}
		
		winCondition(s, player_name, next_player_name, player_color);

	}
	public void winCondition(Spot s, String player_name, String next_player_name, Color player_color) {
		
		for (int i = 0; i < 3; i++) {
			
            if (_board.getSpotAt(i, 0).getSpotColor() == player_color &&
                    _board.getSpotAt(i, 1).getSpotColor() == player_color &&
                    _board.getSpotAt(i, 2).getSpotColor() == player_color) {
                _game_won = true;
            }
            if (_board.getSpotAt(0, i).getSpotColor() == player_color &&
                    _board.getSpotAt(1, i).getSpotColor() == player_color &&
                    _board.getSpotAt(2, i).getSpotColor() == player_color) {
                _game_won = true;
            }
            if (_board.getSpotAt(0, 0).getSpotColor() == player_color &&
                    _board.getSpotAt(1, 1).getSpotColor() == player_color &&
                    _board.getSpotAt(2, 2).getSpotColor() == player_color) {
                _game_won = true;
            }
            if (_board.getSpotAt(0, 2).getSpotColor() == player_color &&
                    _board.getSpotAt(1, 1).getSpotColor() == player_color &&
                    _board.getSpotAt(2, 0).getSpotColor() == player_color) {
                _game_won = true;
            }
        }

		boolean isFull = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (_board.getSpotAt(i, j).isEmpty()) {
                    isFull = false;
                }
            }
        }

        if (_game_won) {
            _message.setText(player_name + " got three in a row. Game over.");
        } else if (isFull && !_game_won) {
            _message.setText("Draw. Restart to play again.");
        } else {
            _message.setText(next_player_name + " to play.");
        }
	}
	
	public void spotEntered(Spot spot) {
        if (spot.isEmpty() && !_game_won) {
            spot.highlightSpot();
        } else {
            return;
        }
	}

	public void spotExited(Spot spot) {
		// TODO Auto-generated method stub
        spot.unhighlightSpot();

	}
	
	public void actionPerformed(ActionEvent arg0) {
        resetGame();
	}
}
