// ouverture de la socket pour communiquer avec le serveur
let socket = new WebSocket("ws://localhost:3232");

function shortname(s) {
    return s.replace(/[^\w]/g, '').toLowerCase();
}

function clickButton(event) {
    socket.send(event.target.innerText);
}

class Main extends React.Component {
    constructor(props) {
        super(props);
        this.state = null;
    }

    componentDidMount() {
        socket.onmessage = event => {
            let data = JSON.parse(event.data);
            console.log(data);
            this.setState(data);
        };
    }

    render() {
        if (this.state === null) {
            return React.createElement(
                'div',
                null,
                'Not connected to an active game.'
            );
        }

        // process players data
        for (let data of this.state.game.players) {
            data.turn_player = this.state.game.turn_player === data.name; // identify turn player
            data.active_player = this.state.active_player === data.name; // identify active player
            if ('weapon' in data) {
                data.in_play.unshift(data.weapon);
            }
        }

        let classes = [];
        if (this.state.game.players.length >= 5) {
            classes.push("row3");
        } else {
            classes.push("row2");
        }

        let buttons = [];
        for (const [i, button] of this.state.buttons.entries()) {
            buttons.push(React.createElement(PlayerButton, { label: button, value: i + 1 }));
        }

        return React.createElement(
            'div',
            { id: 'main', className: classes.join(" ") },
            React.createElement(PlayersArea, { players: this.state.game.players }),
            React.createElement(
                'div',
                { id: 'instruction' },
                React.createElement(
                    'span',
                    { id: 'instruction_text' },
                    this.state.instruction
                ),
                buttons,
                this.state.can_pass && React.createElement(PlayerButton, { label: 'Pass', value: '' })
            ),
            React.createElement(
                'div',
                { id: 'piles' },
                React.createElement(DrawPile, { size: this.state.game.draw_pile }),
                React.createElement(
                    'div',
                    { id: 'discard_pile' },
                    React.createElement(CardsList, { cards: this.state.game.discard_pile, keepOrder: true })
                )
            )
        );
    }
}

class PlayersArea extends React.Component {
    render() {
        const players = this.props['players'].map(d => React.createElement(PlayerInfo, { data: d }));
        console.log(players.length);
        if (players.length > 3) {
            return React.createElement(
                'div',
                { className: 'players_area' },
                React.createElement(
                    'div',
                    { className: 'row' },
                    players.slice(0, ~~(players.length / 2))
                ),
                React.createElement(
                    'div',
                    { className: 'row' },
                    players.slice(~~(players.length / 2)).reverse()
                )
            );
        } else {
            return React.createElement(
                'div',
                { className: 'players_area' },
                React.createElement(
                    'div',
                    { className: 'row' },
                    players
                )
            );
        }
    }
}

class HPBullets extends React.Component {
    render() {
        let bullets = [];
        for (let i = 0; i < this.props.hp; i++) {
            bullets.push(React.createElement('div', { className: 'hp_bullet', style: { backgroundImage: `url(images/bullet.png)` } }));
        }
        for (let i = this.props.hp; i < this.props.hp_max; i++) {
            bullets.push(React.createElement('div', { className: 'hp_bullet', style: { backgroundImage: `url(images/bullet_grey.png)` } }));
        }

        return React.createElement(
            'div',
            { className: 'hp' },
            bullets
        );
    }
}

class PlayerButton extends React.Component {
    render() {
        let className = "button";
        let onClick = event => {
            socket.send(this.props.value);
            event.preventDefault();
        };

        return React.createElement(
            'span',
            {
                className: className,
                onClick: onClick
            },
            this.props.label
        );
    }
}

class Card extends React.Component {
    render() {
        const [name, poker] = this.props.data;
        const filename = shortname(name) + '_' + poker;

        return React.createElement(
            'div',
            {
                className: ['card'].concat(this.props.classes).join(' '),
                style: {
                    backgroundImage: `url(images/cards/${filename}.png)`,
                    zIndex: `${this.props.zindex}`
                },
                onClick: () => {
                    socket.send(name);
                },
                ref: 'bg'
            },
            React.createElement(
                'span',
                { className: 'name', ref: 'name' },
                name
            )
        );
    }

    componentDidMount() {
        let bg = this.refs.bg;
        let name = this.refs.name;
        if (name.clientWidth / bg.clientWidth > .75) {
            name.style.transform = `scale(${.75 * bg.clientWidth / name.clientWidth}, 1)`;
        }
    }
}

class CharacterCard extends React.Component {
    render() {
        return React.createElement(
            'div',
            {
                className: 'card character',
                style: { backgroundImage: `url(images/characters/${shortname(this.props.name)}.png)` },
                ref: 'bg'
            },
            React.createElement(
                'span',
                { ref: 'name', className: 'name' },
                this.props.name
            )
        );
    }

    componentDidMount() {
        let bg = this.refs.bg;
        let name = this.refs.name;
        if (name.clientWidth / bg.clientWidth > .75) {
            name.style.transform = `scale(${.75 * bg.clientWidth / name.clientWidth}, 1)`;
        }
    }
}

class PlayerInfo extends React.Component {
    render() {
        let classes = ['player_info'];
        if (this.props.data['active_player']) {
            classes.push('active');
        }
        if (this.props.data['turn_player']) {
            classes.push('turn_player');
        }

        return React.createElement(
            'div',
            { className: classes.join(' ') },
            React.createElement(
                'div',
                { className: 'player_name' },
                this.props.data['name']
            ),
            React.createElement(
                'div',
                { className: 'row' },
                React.createElement(
                    'div',
                    {
                        className: 'character_info',
                        onClick: () => {
                            socket.send(this.props.data['name']);
                        }
                    },
                    React.createElement('div', {
                        className: 'card role',
                        style: { backgroundImage: `url(images/roles/${this.props.data['role']}.png)` }
                    }),
                    React.createElement(CharacterCard, { name: this.props.data['character'] }),
                    React.createElement(HPBullets, { hp: this.props.data['hp'], hp_max: this.props.data['hp_max'] })
                ),
                React.createElement(
                    'div',
                    { className: 'in_play' },
                    React.createElement(CardsList, { cards: this.props.data['in_play'] })
                )
            ),
            React.createElement(
                'div',
                { className: 'hand' },
                React.createElement(CardsList, { cards: this.props.data['hand'] })
            )
        );
    }
}

class CardsList extends React.Component {
    render() {
        // if list is empty return an empty "phantom" card to keep space
        if (this.props.cards.length === 0) {
            return [React.createElement(
                'div',
                { className: 'card phantom', key: 'phantom' },
                React.createElement(
                    'div',
                    { className: 'wrapper' },
                    React.createElement('div', { className: 'background' })
                )
            )];
        }

        let cards = [];
        if (!this.props.keepOrder) {
            this.props.cards.sort((x, y) => x[0].localeCompare(y[0]));
        }

        for (const [i, data] of this.props.cards.entries()) {
            let classes = [];
            if (i + 1 < this.props.cards.length && data[0] === this.props.cards[i + 1][0]) {
                classes.push('duplicate');
            }
            cards.push(React.createElement(Card, { data: data, classes: classes, zindex: i + 1, index: i }));
        }
        return cards;
    }
}

class DrawPile extends React.Component {
    render() {
        if (this.props.size === 0) {
            return [React.createElement(
                'div',
                { className: 'card phantom', key: 'phantom' },
                React.createElement(
                    'div',
                    { className: 'wrapper' },
                    React.createElement('div', { className: 'background' })
                )
            )];
        }

        let cards = [];
        for (let i = 0; i < this.props.size; i++) {
            cards.push(React.createElement('div', { className: 'card', style: { backgroundImage: "url(images/cards/back.png)" } }));
        }
        return React.createElement(
            'div',
            { id: 'draw_pile' },
            cards
        );
    }
}

ReactDOM.render(React.createElement(Main, null), document.getElementById('root'));

//# sourceMappingURL=index.js.map