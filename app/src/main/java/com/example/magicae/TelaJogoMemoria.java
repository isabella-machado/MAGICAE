package com.example.magicae;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button; // Correção: Import para Button estava faltando
import android.widget.GridLayout;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;

public class TelaJogoMemoria extends AppCompatActivity {

    // --- Views ---
    private GridLayout gameGrid;
    private Button btnVoltar; // O botão que aparecerá no final

    // --- Game Configuration ---
    private static final int NUM_COLUMNS = 4;
    private static final int NUM_ROWS = 5;
    private static final int NUM_CARDS = NUM_COLUMNS * NUM_ROWS; // 20 cartas

    // --- Game State Variables ---
    private ArrayList<MemoryCard> cards;
    private MemoryCard firstSelectedCard = null;
    private MemoryCard secondSelectedCard = null;
    private boolean isChecking = false; // Impede cliques enquanto verifica um par
    private int matchedPairs = 0; // Correção: Adicionado contador de pares

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_jogo_memoria);

        // Inicializa as views
        gameGrid = findViewById(R.id.gameGrid);
        btnVoltar = findViewById(R.id.btnVoltar);

        // Configura o grid
        gameGrid.setColumnCount(NUM_COLUMNS);
        gameGrid.setRowCount(NUM_ROWS);

        // Configura o clique do botão para voltar à tela de seleção
        btnVoltar.setOnClickListener(v -> navigateToSelectionScreen());

        // Inicia a lógica do jogo
        initializeGame();
    }

    /**
     * Prepara o tabuleiro, embaralha as cartas e as adiciona na tela.
     */
    private void initializeGame() {
        // 1. Defina 10 imagens únicas para formar 10 pares.
        int[] faces = {
                R.drawable.a, R.drawable.e, R.drawable.i, R.drawable.o, R.drawable.u,
                R.drawable.a, R.drawable.e, R.drawable.i, R.drawable.o, R.drawable.u // Exemplo, use 10 imagens diferentes
        };

        // 2. Correção: Lógica para criar os pares de cartas corretamente
        cards = new ArrayList<>();
        for (int i = 0; i < NUM_CARDS / 2; i++) {
            // Adiciona duas cartas com a mesma imagem para formar um par
            cards.add(new MemoryCard(faces[i]));
            cards.add(new MemoryCard(faces[i]));
        }

        // 3. Embaralha as cartas
        Collections.shuffle(cards);

        // 4. Cria as ImageViews para cada carta e adiciona ao grid
        for (final MemoryCard card : cards) {
            ImageView cardView = new ImageView(this);
            card.setCardView(cardView);

            // Define as propriedades visuais da carta
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = 0;
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1, 1f);
            params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1, 1f);
            params.setMargins(8, 8, 8, 8);
            cardView.setLayoutParams(params);

            cardView.setImageResource(R.drawable.autismo); // Imagem do verso da carta
            cardView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            cardView.setOnClickListener(this::onCardClicked);

            gameGrid.addView(cardView);
        }
    }

    /**
     * Lida com a lógica de clique em uma carta.
     */
    public void onCardClicked(View view) {
        if (isChecking || !(view instanceof ImageView)) {
            return;
        }

        MemoryCard clickedCard = findCardByView(view);

        if (clickedCard == null || clickedCard.isMatched() || clickedCard.isFaceUp()) {
            return;
        }

        clickedCard.setFaceUp(true);

        if (firstSelectedCard == null) {
            firstSelectedCard = clickedCard;
        } else {
            secondSelectedCard = clickedCard;
            isChecking = true; // Bloqueia cliques
            checkForMatch();
        }
    }

    /**
     * Verifica se as duas cartas viradas são um par.
     */
    private void checkForMatch() {
        if (firstSelectedCard.getFaceId() == secondSelectedCard.getFaceId()) {
            // É um par!
            firstSelectedCard.setMatched(true);
            secondSelectedCard.setMatched(true);
            matchedPairs++; // Correção: Incrementa o contador de pares
            resetSelection();
            checkGameEnd(); // Correção: Verifica se o jogo terminou
        } else {
            // Não é um par. Vira as cartas de volta após 1 segundo.
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                if (firstSelectedCard != null) firstSelectedCard.setFaceUp(false);
                if (secondSelectedCard != null) secondSelectedCard.setFaceUp(false);
                resetSelection();
            }, 1000);
        }
    }

    /**
     * Correção: Verifica se todos os pares foram encontrados.
     */
    private void checkGameEnd() {
        if (matchedPairs == (NUM_CARDS / 2)) {
            // Jogo terminou! Mostra o botão para voltar.
            btnVoltar.setVisibility(View.VISIBLE);
        }
    }

    private void resetSelection() {
        firstSelectedCard = null;
        secondSelectedCard = null;
        isChecking = false; // Libera os cliques
    }

    private MemoryCard findCardByView(View view) {
        for (MemoryCard card : cards) {
            if (card.getCardView() == view) {
                return card;
            }
        }
        return null;
    }

    private void navigateToSelectionScreen() {
        Intent intent = new Intent(TelaJogoMemoria.this, TelaSelecionar.class);
        startActivity(intent);
        finish(); // Fecha a tela do jogo para que o usuário não possa voltar
    }

    /**
     * Classe interna para representar uma carta do jogo.
     */
    private static class MemoryCard {
        private final int faceId;
        private boolean isFaceUp = false;
        private boolean isMatched = false;
        private ImageView cardView;

        MemoryCard(int faceId) {
            this.faceId = faceId;
        }

        public int getFaceId() { return faceId; }
        public boolean isFaceUp() { return isFaceUp; }
        public boolean isMatched() { return isMatched; }
        public ImageView getCardView() { return cardView; }

        public void setCardView(ImageView cardView) { this.cardView = cardView; }
        public void setMatched(boolean matched) { isMatched = matched; }

        public void setFaceUp(boolean faceUp) {
            isFaceUp = faceUp;
            if (cardView != null) {
                cardView.setImageResource(isFaceUp ? faceId : R.drawable.autismo);
            }
        }
    }
}
