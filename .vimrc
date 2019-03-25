set nocompatible              " be iMproved, required
filetype off                  " required

" set the runtime path to include Vundle and initialize
set rtp+=~/.vim/bundle/Vundle.vim
call vundle#begin()
" alternatively, pass a path where Vundle should install plugins
"call vundle#begin('~/some/path/here')

" let Vundle manage Vundle, required
Plugin 'VundleVim/Vundle.vim'

" The following are examples of different formats supported.
" Keep Plugin commands between vundle#begin/end.
" plugin on GitHub repo
Plugin 'tpope/vim-fugitive'
" plugin from http://vim-scripts.org/vim/scripts.html
" Plugin 'L9'
" Git plugin not hosted on GitHub
Plugin 'git://git.wincent.com/command-t.git'
" git repos on your local machine (i.e. when working on your own plugin)
Plugin 'file:///home/gmarik/path/to/plugin'
" The sparkup vim script is in a subdirectory of this repo called vim.
" Pass the path to set the runtimepath properly.
Plugin 'rstacruz/sparkup', {'rtp': 'vim/'}
" Install L9 and avoid a Naming conflict if you've already installed a
" different version somewhere else.
" Plugin 'ascenator/L9', {'name': 'newL9'}

Plugin 'scrooloose/nerdtree'

Plugin 'scrooloose/syntastic'

Plugin 'ervandew/eclim'

Plugin 'vim-airline/vim-airline'

Plugin 'jlanzarotta/bufexplorer'

Plugin 'godlygeek/tabular'

Plugin 'kien/ctrlp.vim'
" All of your Plugins must be added before the following line
call vundle#end()            " required
filetype plugin indent on    " required
" To ignore plugin indent changes, instead use:
"filetype plugin on
"
" Brief help
" :PluginList       - lists configured plugins
" :PluginInstall    - installs plugins; append `!` to update or just :PluginUpdate
" :PluginSearch foo - searches for foo; append `!` to refresh local cache
" :PluginClean      - confirms removal of unused plugins; append `!` to auto-approve removal
"
" see :h vundle for more details or wiki for FAQ
" Put your non-Plugin stuff after this line

" F9/F10 compile/run default file.
" F11/F12 compile/run alternate file.

map <F7> :set makeprg=javac\ %<CR>:make<CR>
map <F8> :!echo %\|awk -F. '{print $1}'\|xargs java<CR>
map <F11> :set makeprg=javac\ #<CR>:make<CR>
map <F12> :!echo #\|awk -F. '{print $1}'\|xargs java<CR>

map! <F7> <Esc>:set makeprg=javac\ %<CR>:make<CR>
map! <F8> <Esc>:!echo %\|awk -F. '{print $1}'\|xargs java<CR>
map! <F11> <Esc>set makeprg=javac\ #<CR>:make<CR>
map! <F12> <Esc>!echo #\|awk -F. '{print $1}'\|xargs java<CR>

" Tip: load a file into the default buffer, and its driver
" into the alternate buffer, then use F9/F12 to build/run.
" Note: # (alternate filename) isn't set until you :next to it!
" Tip2: You can make then run without hitting ENTER to continue. F9-F12

" With these you can cl/cn/cp (quickfix commands) to browse the errors
" after you compile it with :make

set makeprg=javac\ %
set errorformat=%A:%f:%l:\ %m,%-Z%p^,%-C%.%#

" If two files are loaded, switch to the alternate file, then back.
" That sets # (the alternate file).
if argc() == 2
  n
  e #
endif

imap jj <Esc>
map <C-n> :NERDTreeToggle<CR>

filetype plugin indent on
" show existing tab with 4 spaces width
set tabstop=4
" when indenting with '>', use 4 spaces width
set shiftwidth=4
" On pressing tab, insert 4 spaces
set expandtab

:set number

let NERDTreeIgnore = ['\.class$']
